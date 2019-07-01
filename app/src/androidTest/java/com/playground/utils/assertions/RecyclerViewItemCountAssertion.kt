package com.playground.utils.assertions

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import com.airbnb.epoxy.EpoxyRecyclerView
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat


class RecyclerViewItemCountAssertion private constructor(private val expectedCount: Int) : ViewAssertion {

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as EpoxyRecyclerView
        val adapter = recyclerView.adapter

        assertThat(adapter?.itemCount, `is`(expectedCount))
    }

    companion object {

        fun withItemCount(expectedCount: Int): RecyclerViewItemCountAssertion {
            return RecyclerViewItemCountAssertion(expectedCount)
        }

    }
}