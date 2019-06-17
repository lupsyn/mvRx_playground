package com.playground.data

import com.playground.base.TestBase
import com.playground.data.db.GitHubResponsesDao
import com.playground.data.db.utils.TimeProvider
import com.playground.models.GitRepo
import com.playground.network.GitHubService
import com.tui.tda.test.KotlinTypeSafeMatchers
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.Mockito
import java.net.UnknownHostException

private const val EXPIRED_TIME = (40 * 1000).toLong()
private const val CURRENT_TIME = (10 * 1000).toLong()
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

    private lateinit var underTest: GitHubRepositoryImpl
    private val request = GitHubRepositoryImpl.GitHubRepositoryRequest(ORGANIZATION, DEFAULT_PAGE)

    private fun genericError(): Throwable = UnknownHostException("Unknown host")

    @Before
    fun setUp() {
        underTest = GitHubRepositoryImpl(dao, api, timeProvider)
    }

    @Test
    fun shouldGoWithErrorIfDBEmpty() {
        whenAskingForCurrentTimeWillReturnDefaultOne()
        whenSearchThrowException(genericError())
        whenAskingForNetworkWillReturn(ORGANIZATION, DEFAULT_PAGE, Single.just(response))

        underTest.fetchRepositories(ORGANIZATION, DEFAULT_PAGE).test()

        verify(api).fetchRepositories(ORGANIZATION, page = DEFAULT_PAGE)
        verify(dao).saveResponseEntity(KotlinTypeSafeMatchers.any())
    }

    private fun whenSearchThrowException(error: Throwable) {
        given(dao.getResponseEntity(request.hashCode())).willReturn(Single.error(error))
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
