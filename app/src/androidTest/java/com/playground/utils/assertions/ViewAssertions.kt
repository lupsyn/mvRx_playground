package com.playground.utils.assertions

import android.view.View
import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.playground.R
import com.playground.utils.SystemUtils.getCurrentActivity
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*

object ViewAssertions {

    fun assertDisplayingView(viewMatcher: Matcher<View>) {
        // Wrapping viewMatcher with isDisplayed() covers for cases when there're multiple views that matched in the view hierarchy
        onView(allOf(viewMatcher, isDisplayed())).check(matches(isDisplayed()))
    }

    fun assertDisplayingViews(vararg viewMatchers: Matcher<View>) {
        viewMatchers.forEach {
            onView(it).check(matches(isDisplayed()))
        }
    }

    fun assertNotDisplayingView(viewMatcher: Matcher<View>) {
        onView(isRoot()).check(matches(not(hasDescendant(allOf(viewMatcher, isDisplayed())))))
    }

    fun hasDescendantWithText(text: String): Matcher<View> = hasDescendant(withText(text))

    fun assertDisplayingText(@IdRes id: Int, text: String) {
        Espresso.onView(withId(id)).check(matches(withText(text)))
    }

    fun assertContainsText(@IdRes id: Int, text: String) {
        Espresso.onView(withId(id)).check(matches(withText(containsString(text))))
    }

    fun getSubViewAtPosition(position: Int): View? {
        val layoutMyLayout = getCurrentActivity()?.findViewById(R.id.recycler_view) as LinearLayout
        for (x in 0 until layoutMyLayout.childCount) {
            val viewChild1 = layoutMyLayout.getChildAt(x)
            val classChild1 = viewChild1.javaClass
            if (classChild1 == LinearLayout::class.java) {
                val layoutChild1 = viewChild1 as LinearLayout
                for (y in 0 until layoutChild1.childCount) {
                    if (y == position) {
                        viewChild1
                    }

                }
            }
        }
        return null
    }
}
