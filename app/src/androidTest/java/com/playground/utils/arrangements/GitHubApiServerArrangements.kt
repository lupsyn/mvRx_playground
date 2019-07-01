package com.playground.utils.arrangements

import com.playground.utils.MockResponses.errorClientTimeout
import com.playground.utils.MockResponses.gitHubListPage1Pagination
import com.playground.utils.MockResponses.gitHubListPage2Pagination
import com.playground.utils.MockResponses.notFoundError
import com.playground.utils.TestData.ORGANIZATION
import com.playground.utils.matchers.RecordedRequestMatchers.pathContains
import com.playground.utils.matchers.RecordedRequestMatchers.withQueryParam
import com.playground.utils.mockwebserver.MockResponseBuilder.Companion.withNotFoundErrorResponse
import com.playground.utils.mockwebserver.MockResponseBuilder.Companion.withResponse
import com.playground.utils.mockwebserver.MockResponseBuilder.Companion.withSuccessfulResponse
import com.playground.utils.mockwebserver.MockWebServerRule
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anyOf
import java.net.HttpURLConnection

private const val PATH_REPO = "/orgs/$ORGANIZATION/repos?"


class GitHubApiServerArrangements(serverRule: MockWebServerRule) : ApiServerArrangements(serverRule) {


    fun returnErrorOnFetchingRepositories() {
        val requestMatcher = allOf(
            pathContains(PATH_REPO),
            withQueryParam("pageNumber", "1")
        )

        respondOn(
            requestMatcher,
            withResponse()
                .responseCode(HttpURLConnection.HTTP_CLIENT_TIMEOUT)
                .body(errorClientTimeout)
        )
    }


    fun returnsReposListPageOneSuccessfully() {
        //https://api.github.com/orgs/square/repos?&page=1&per_page=20
        val requestMatcher = anyOf(
            pathContains(PATH_REPO),
            withQueryParam("pageNumber", "1"),
            withQueryParam("per_page", "20")
        )

        respondOn(requestMatcher, withSuccessfulResponse().body(gitHubListPage1Pagination))
    }

    fun returnsReposListPageTwoSuccessfully() {
        val requestMatcher = allOf(
            pathContains(PATH_REPO),
            withQueryParam("pageNumber", "2")
        )

        respondOn(requestMatcher, withSuccessfulResponse().body(gitHubListPage2Pagination))
    }

    fun returnsRepoListPage2Error() {
        val requestMatcher = allOf(
            pathContains(PATH_REPO),
            withQueryParam("pageNumber", "2")
        )
        respondOn(
            requestMatcher,
            withNotFoundErrorResponse().body(notFoundError)
        )
    }


}