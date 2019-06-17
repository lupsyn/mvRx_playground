package com.playground.data

import com.playground.base.TestBase
import com.playground.data.db.GitHubResponsesDao
import com.playground.data.db.GitHubResponsesEntity
import com.playground.data.db.utils.TimeProvider
import com.playground.models.GitRepo
import com.playground.network.CacheExpirationTime.MINUTE_TO_MILLISECONDS
import com.playground.network.GitHubService
import com.playground.utils.TestApiErrors
import com.playground.utils.TestApiErrors.AN_ERROR
import com.tui.tda.test.KotlinTypeSafeMatchers.any
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.Mock

private const val EXPIRED_TIME = (40 * MINUTE_TO_MILLISECONDS).toLong()
private const val CURRENT_TIME = (10 * MINUTE_TO_MILLISECONDS).toLong()
private const val DEFAULT_PAGE = 0
private const val ORGANIZATION = "square"

class GitHubRepositoryImplTest : TestBase() {
    @Mock
    private lateinit var dao: GitHubResponsesDao
    @Mock
    private lateinit var api: GitHubService
    @Mock
    private lateinit var timeProvider: TimeProvider
    @Mock
    private lateinit var response: List<GitRepo>
    @Mock
    private lateinit var gitHubResponseEntity: GitHubResponsesEntity

    private lateinit var underTest: GitHubRepositoryImpl
    private val request = GitHubRepositoryImpl.GitHubRepositoryRequest(ORGANIZATION, DEFAULT_PAGE)

    @Before
    fun setUp() {
        underTest = GitHubRepositoryImpl(dao, api, timeProvider)
    }

    @Test
    fun shouldEmitErrorIfDbIsEmptyAndNetworkCallFails() {
        whenAskingForCurrentTimeWillReturnDefaultOne()
        whenSearchThrowException(TestApiErrors.emptyDbError())
        given(
            api.fetchRepositories(
                ORGANIZATION,
                page = DEFAULT_PAGE
            )
        ).willReturn(Single.error(AN_ERROR))

        underTest.fetchRepositories(ORGANIZATION, DEFAULT_PAGE)
            .test()
            .assertError(AN_ERROR)

        verify(api).fetchRepositories(ORGANIZATION, page = DEFAULT_PAGE)
    }

    @Test
    fun shouldGoWithErrorIfDBEmpty() {
        whenAskingForCurrentTimeWillReturnDefaultOne()
        whenSearchThrowException(TestApiErrors.emptyDbError())
        whenAskingForNetworkWillReturn(ORGANIZATION, DEFAULT_PAGE, Single.just(response))

        underTest.fetchRepositories(ORGANIZATION, DEFAULT_PAGE)
            .test()
            .assertComplete()

        verify(api).fetchRepositories(ORGANIZATION, page = DEFAULT_PAGE)
        verify(dao).saveResponseEntity(any())
    }

    @Test
    fun shouldCallApiAndSaveResponseWhenDbEntityExpired() {
        whenAskingForNetworkWillReturn(ORGANIZATION, DEFAULT_PAGE, Single.just(response))
        whenLocalStorageHasSearchResponse(gitHubResponseEntity, expiredTime = true)

        underTest.fetchRepositories(ORGANIZATION, DEFAULT_PAGE)
            .test()
            .assertValue(response)
            .assertComplete()

        verify(api).fetchRepositories(ORGANIZATION, page = DEFAULT_PAGE)
        verify(dao).saveResponseEntity(any())
    }

    private fun whenSearchThrowException(error: Throwable) {
        given(dao.getResponseEntity(anyInt())).willReturn(Single.error(error))
    }

    private fun whenLocalStorageHasSearchResponse(
        gitHubResponseEntity: GitHubResponsesEntity,
        expiredTime: Boolean
    ) {
        given(dao.getResponseEntity(request.hashCode())).willReturn(Single.just(gitHubResponseEntity))
        if (expiredTime) {
            given(gitHubResponseEntity.timeStamp).willReturn(EXPIRED_TIME)
        }
    }

    private fun whenAskingForCurrentTimeWillReturnDefaultOne() {
        given(timeProvider.currentTime()).willReturn(CURRENT_TIME)
    }

    private fun whenAskingForNetworkWillReturn(
        organization: String,
        page: Int,
        response: Single<List<GitRepo>>
    ) {
        given(api.fetchRepositories(organization, page = page)).willReturn(response)
    }
}
