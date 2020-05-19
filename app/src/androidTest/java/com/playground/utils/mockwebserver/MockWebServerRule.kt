package com.playground.utils.mockwebserver

import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.Matcher
import org.junit.rules.ExternalResource
import java.io.IOException

/**
 * Used to initiate and program an HTTP server running locally on the device.
 * All API requests from the app need to be directed to this server,
 * so we maintain full control over the responses served.
 *
 * The server is lightweight enough to be started / stopped for each test.
 */
class MockWebServerRule : ExternalResource() {

    private var server: MockWebServer? = null
    private var mockDispactcher: MockDispatcher? = null

    override fun before() {
        if (mockDispactcher == null) {
            mockDispactcher = MockDispatcher()
        }
        if (server == null) {
            server = MockWebServer().also {
                it.start(SERVER_PORT)
                it.dispatcher = mockDispactcher as MockDispatcher
            }
        } else {
            mockDispactcher = MockDispatcher()
            server?.dispatcher = mockDispactcher as MockDispatcher
        }
    }

    fun shutdownServer() {
        try {
            server?.shutdown()
            mockDispactcher?.shutdown()
            server = null
            mockDispactcher = null
        } catch (e: IOException) {
            throw RuntimeException("Failed to shutdown MockWebServer", e)
        }
    }

    /**
     * Add a new response to the mock server.
     */
    fun respondsWith(mockResponseBuilder: MockResponseBuilder) = ResponseSetup(mockResponseBuilder)

    /**
     * Just adds sugar to the syntax for setting up a server response.
     * It allows for syntax like:
     *
     * _serverRule.respondsWith(awesomeResponse).on(requestMatcher)_
     */
    inner class ResponseSetup(private val responseBuilder: MockResponseBuilder) {
        fun on(requestMatcher: Matcher<in RecordedRequest>) {
            mockDispactcher?.responds(requestMatcher, responseBuilder)
        }
    }

    companion object {
        const val SERVER_PORT = 9999
    }
}