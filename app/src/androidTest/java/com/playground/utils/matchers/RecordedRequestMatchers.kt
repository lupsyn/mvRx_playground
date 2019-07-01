package com.playground.utils.matchers

import android.net.Uri
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeDiagnosingMatcher

object RecordedRequestMatchers {

    /**
     * Match requests that _contain_ the given path.
     */
    fun pathContains(path: String): Matcher<RecordedRequest> {
        return object : TypeSafeDiagnosingMatcher<RecordedRequest>() {
            override fun matchesSafely(recordedRequest: RecordedRequest, mismatchDescription: Description): Boolean {
                return recordedRequest.path.contains(path)
            }

            override fun describeTo(description: Description) {
                description.appendText("a request that contains the path ").appendValue(path)
            }
        }
    }

    /**
     * Match requests of the given HTTP [method].
     */
    fun requestMethodIs(method: HttpMethod): Matcher<RecordedRequest> {
        return object : TypeSafeDiagnosingMatcher<RecordedRequest>() {
            override fun matchesSafely(recordedRequest: RecordedRequest, mismatchDescription: Description): Boolean {
                val match = method.name.equals(recordedRequest.method, ignoreCase = true)
                if (!match) {
                    mismatchDescription.appendText("request HTTP method was ").appendValue(recordedRequest.method)
                }

                return match
            }

            override fun describeTo(description: Description) {
                description.appendText("a request with HTTP method ").appendValue(method)
            }
        }
    }

    /**
     * Match requests that _contain_ the given key and value query parameter.
     */
    fun withQueryParam(queryKey: String, queryValue: String): Matcher<RecordedRequest> {
        return object : TypeSafeDiagnosingMatcher<RecordedRequest>() {
            override fun matchesSafely(recordedRequest: RecordedRequest, mismatchDescription: Description): Boolean {

                val uri = Uri.parse(recordedRequest.path)
                val uriValue = uri.getQueryParameter(queryKey)
                val found = uriValue == queryValue

                if (!found) {
                    if (uriValue == null) {
                        mismatchDescription.appendText("query parameter ").appendValue(queryKey).appendText(" is not present in the path")
                    } else {
                        mismatchDescription.appendText("the query parameter has the value ").appendValue(uriValue).appendText(" instead of ").appendValue(queryValue)
                    }
                }
                return found
            }

            override fun describeTo(description: Description) {
                description.appendText("a request that contains a query param ").appendValue(queryKey).appendText(" with value ").appendValue(queryValue)
            }
        }
    }

    /**
     * Wrap the given [matcher] in one that will *only match once*.
     * All subsequent checks will fail.
     */
    fun oneCallTo(matcher: Matcher<in RecordedRequest>): Matcher<RecordedRequest> {
        return object : TypeSafeDiagnosingMatcher<RecordedRequest>() {
            private var matchCount = 0

            override fun matchesSafely(recordedRequest: RecordedRequest, mismatchDescription: Description): Boolean {
                if (matchCount == 1) {
                    mismatchDescription.appendText("request $recordedRequest already matched before")
                    return false
                }

                val matched = matcher.matches(recordedRequest)
                if (matched) {
                    matchCount++
                }

                return matched
            }

            override fun describeTo(description: Description) {
                description.appendText("One call to ").appendDescriptionOf(matcher)
            }
        }
    }
}