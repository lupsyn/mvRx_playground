package com.playground

import com.playground.base.BaseInstrumentationTestCase
import org.junit.Test


class RepositoryListUiTest : BaseInstrumentationTestCase() {

    @Test
    fun willDisplayFirstPaginatedListCorrectly() {
        given.gitHubServer.returnsReposListPageOneSuccessfully()

        `when`.user.launchesTheApp()

        then.user.sees.repositoriesListScreenAssertion.withSuccessfullyLoadedFirstPageItem()
    }


    @Test
    fun willDisplayErrorCorrectly() {
        given.gitHubServer.returnErrorOnFetchingRepositories()

        `when`.user.launchesTheApp()

        then.user.sees.repositoriesListScreenAssertion.withErrorState()
    }
}
