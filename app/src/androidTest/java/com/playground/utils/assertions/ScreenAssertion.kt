package com.playground.utils.assertions

/**
 * Wrapper for various screen assertions.
 *
 * Typical usage is:
 * _then.user.sees.someScreenIsDisplayed()_
 */
class ScreenAssertion {

    val repositoriesListScreenAssertion by lazy { RepositoriesListScreenAssertions() }

}
