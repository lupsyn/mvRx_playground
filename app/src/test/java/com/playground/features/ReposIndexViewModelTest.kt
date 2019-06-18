package com.playground.features

import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.test.DebugMode
import com.airbnb.mvrx.test.MvRxTestRule
import com.airbnb.mvrx.withState
import com.playground.MvRxApplication
import com.playground.base.TestBase
import com.playground.data.GitHubRepository
import com.playground.models.GitRepo
import com.playground.utils.TestApiErrors
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Assert.*
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.Mock


class ReposIndexViewModelTest : TestBase() {

    private lateinit var underTest: ReposIndexViewModel
    @Mock
    private lateinit var repository: GitHubRepository
    @Mock
    private lateinit var repoOne: GitRepo

    private lateinit var subListRepo: List<GitRepo>

    companion object {
        @JvmField
        @ClassRule
        val mvRxTestRule = MvRxTestRule(DebugMode.Unset, setRxImmediateSchedulers = true)
    }

    @Before
    fun setUp() {
        subListRepo = listOf(repoOne)
    }

    @Test
    fun shouldUpdateStatusOnFirstRequestFromLoadindToSuccess() {
        val initialState = ReposIndexState()
        val repoSubject = SingleSubject.create<List<GitRepo>>()
        whenFetchRepositoriesThenReturn(repoSubject)

        underTest = ReposIndexViewModel(initialState, repository)

        //Verify after set initial state this one is set as Loading
        withState(underTest) { assertTrue(it.request is Loading) }

        //Verify repository is called for the first page
        verify(repository).fetchRepositories(
            organization = MvRxApplication.ORGANIZATION,
            page = subListRepo.size / MvRxApplication.REPOS_PER_PAGE + 1
        )

        // New emission from the data source happened
        repoSubject.onSuccess(subListRepo)

        // verify that tasks request was successful and the repo list is present
        withState(underTest) {
            assertTrue(it.request is Success)
            assertEquals(it.repos, subListRepo)
        }
        // verify that loading state has changed to false after the stream is completed
        withState(underTest) { assertFalse(it.request is Loading) }
    }

    @Test
    fun shouldChangeToFailureStateInCaseOfErrorEmission() {
        val initialState = ReposIndexState()
        whenFetchRepositoriesThenReturn(Single.error(TestApiErrors.AN_ERROR))

        // given the viewmodel with default state
        underTest = ReposIndexViewModel(initialState, repository)

        //Verify repository is called for the first page
        verify(repository).fetchRepositories(
            organization = MvRxApplication.ORGANIZATION,
            page = subListRepo.size / MvRxApplication.REPOS_PER_PAGE + 1
        )

        // verify the tasks request failed and the tasks list is empty
        withState(underTest) {
            assertTrue(it.request is Fail)
            assertEquals(it.repos, emptyList<GitRepo>())
        }
    }

    @Test
    fun shouldCorrectlyFetchSecondPage() {
        val initialState = ReposIndexState()
        val repoSubject = SingleSubject.create<List<GitRepo>>()
        whenFetchRepositoriesThenReturn(repoSubject)

        underTest = ReposIndexViewModel(initialState, repository)

        //Verify after set initial state this one is set as Loading
        withState(underTest) { assertTrue(it.request is Loading) }

        // New emission from the data source happened
        repoSubject.onSuccess(subListRepo)

        // verify that tasks request was successful and the repo list is present
        withState(underTest) {
            assertTrue(it.request is Success)
            assertEquals(it.repos, subListRepo)
        }
        // verify that loading state has changed to false after the stream is completed
        withState(underTest) { assertFalse(it.request is Loading) }

        underTest.fetchNextPage()
        // Second emission from the data source happened
        repoSubject.onSuccess(subListRepo)

        // verify that tasks request was successful and the repo list is present
        withState(underTest) {
            assertTrue(it.request is Success)
            assertEquals(it.repos.size, 2)
            assertEquals(it.repos[0], repoOne)
            assertEquals(it.repos[1], repoOne)
        }

    }


    private fun whenFetchRepositoriesThenReturn(singleResult: Single<List<GitRepo>>) {
        given(repository.fetchRepositories(anyString(), anyInt())).willReturn(singleResult)
    }
}