package com.playground.utils.assertions

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers
import com.playground.R
import com.playground.utils.assertions.RecyclerViewAssertions.assertRecyclerViewMatchersInPositions
import com.playground.utils.assertions.RecyclerViewAssertions.assertRecyclerViewSize
import com.playground.utils.assertions.ViewAssertions.assertDisplayingText
import com.playground.utils.assertions.ViewAssertions.assertDisplayingViews
import com.playground.utils.assertions.ViewAssertions.hasDescendantWithText
import org.hamcrest.Matcher

class RepositoriesListScreenAssertions {

    fun withSuccessfullyLoadedFirstPageItem() {
        try {
            //TODO: sync epoxy controller
            Thread.sleep(1000)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        assertRecyclerViewSize(R.id.recycler_view, 21)

        assertRecyclerViewMatchersInPositions(R.id.recycler_view, successfullyLoadedItem())
    }


    private fun successfullyLoadedItem(): List<Pair<Int, Matcher<View>>> {
        return listOf(
            Pair(0, hasDescendantWithText("simplerrd")),
            Pair(1, hasDescendantWithText("plastic")),
            Pair(2, hasDescendantWithText("ftpfxp")),
            Pair(3, hasDescendantWithText("RubyMinePreferences")),
            Pair(4, hasDescendantWithText("fundraiser")),
            Pair(5, hasDescendantWithText("rack-geo")),
            Pair(6, hasDescendantWithText("rack-time-zone-header")),
            Pair(7, hasDescendantWithText("retrofit")),
            Pair(8, hasDescendantWithText("border_patrol")),
            Pair(9, hasDescendantWithText("rubymine-extensions")),
            Pair(10, hasDescendantWithText("zapp")),
            Pair(11, hasDescendantWithText("cube")),
            Pair(12, hasDescendantWithText("square.github.io")),
            Pair(13, hasDescendantWithText("luhnybin")),
            Pair(14, hasDescendantWithText("cane")),
            Pair(15, hasDescendantWithText("jetpack")),
            Pair(16, hasDescendantWithText("crossfilter")),
            Pair(17, hasDescendantWithText("cubism")),
            Pair(18, hasDescendantWithText("JIRAFoundation")),
            Pair(19, hasDescendantWithText("prodeng"))
        )
    }

    fun withErrorState() {
        assertRecyclerViewSize(R.id.recycler_view, 1)
        assertDisplayingViews(ViewMatchers.withText("An error happened loading repos. Click to retry."))
    }
}
