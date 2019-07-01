package com.playground.utils.assertions

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.playground.utils.EspressoUtils.matches
import com.playground.utils.matchers.RecyclerViewMatcher.Companion.withRecyclerView
import org.hamcrest.Matcher


object RecyclerViewAssertions {

    fun assertRecyclerViewSize(@IdRes recyclerViewId: Int, itemCount: Int) {
        onView(withId(recyclerViewId)).check(RecyclerViewItemCountAssertion.withItemCount(itemCount))
    }

    fun assertRecyclerViewMatchersInPositions(@IdRes recyclerViewId: Int, matchersList: List<Pair<Int, Matcher<View>>>) {
        for (pair in matchersList) {
            onView(withId(recyclerViewId)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(pair.first))
            assertRecyclerViewMatcherInPosition(recyclerViewId, pair.first, pair.second)
        }
    }

    private fun assertRecyclerViewMatcherInPosition(@IdRes recyclerViewId: Int, position: Int, matcher: Matcher<View>) {
        onView(withRecyclerView(recyclerViewId).atPosition(position))
            .check(matches(matcher))
    }

}
