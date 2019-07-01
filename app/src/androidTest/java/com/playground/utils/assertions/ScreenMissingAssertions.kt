package com.playground.utils.assertions

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.playground.utils.assertions.ViewAssertions.assertNotDisplayingView

/**
 * Wrapper for various assertions to verify something is *NOT* visible on the screen.
 *
 * Typical usage is:
 * _then.user.cannotSee.someElement()_
 */
class ScreenMissingAssertions {
    fun manageBookingScreen() {
       // assertNotDisplayingView(ViewMatchers.withId(R.id.cta_container))
    }

    fun updateDialog() {
        assertNotDisplayingView(withId(android.R.id.message))
        assertNotDisplayingView(withId(android.R.id.button1))
    }
}