package com.playground.utils.arrangements

import com.playground.utils.matchers.RecordedRequestMatchers.oneCallTo
import com.playground.utils.matchers.RecordedRequestMatchers.pathContains
import com.playground.utils.mockwebserver.MockResponseBuilder
import com.playground.utils.mockwebserver.MockWebServerRule
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.Matcher

abstract class ApiServerArrangements(private val serverRule: MockWebServerRule) {

    /**
     * Setup a server response rule that will return a **one-time** response on the given path.
     *
     * This handles all needed formatting of the given path
     * so it can be called directly with the raw path of the API call being set up,
     */
    fun respondOn(path: String, response: MockResponseBuilder) {
        respondOn(pathContains(path), response)
    }

    /**
     * Setup a server response rule that will return a **one-time** response on the given path.
     *
     * Common use-case for this is setting multiple matchers for a given request,
     * e.g. to verify the path, HTTP method, query params, etc.
     *
     * If you just want to setup a simple response based on the request path, use [ApiServerArrangements.respondOn] instead.
     */
    fun respondOn(requestMatcher: Matcher<in RecordedRequest>, response: MockResponseBuilder) {
        serverRule.respondsWith(response).on(oneCallTo(requestMatcher))
    }
}