package com.playground.features

import android.os.Bundle
import android.util.Log
import android.view.View
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.fragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.playground.R
import com.playground.core.BaseFragment
import com.playground.core.simpleController
import com.playground.features.views.basicRow
import com.playground.features.views.fullScreenMessageView
import com.playground.features.views.loadingRow
import kotlinx.android.synthetic.main.full_screen_message.view.*

private const val TAG = "ReposIndexFragment"

class ReposIndexFragment : BaseFragment() {

    /**
     * This will get or create a new ViewModel scoped to this Fragment. It will also automatically
     * subscribe to all state changes and call [invalidate] which we have wired up to
     * call [buildModels] in [BaseFragment].
     */
    private val viewModel: ReposIndexViewModel by fragmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.asyncSubscribe(ReposIndexState::request, uniqueOnly = true , onFail = { error ->
            Snackbar.make(coordinatorLayout, "Repos request failed.", Snackbar.LENGTH_LONG)
                .setAction(R.string.dismiss) {}
                .show()
            Log.w(TAG, "Repos request failed", error)
        })
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        state.repos.forEach {
            basicRow {
                id(it.id)
                title(it.full_name)
                subtitle(it.name)
                description(it.description)
            }
        }

        if (state.repos.isEmpty() && state.request !is Loading) {
            fullScreenMessageView {
                id("empty_message")
                title("An error happened loading repos. Click to retry.")
                onClickListener { _ -> viewModel.fetchNextPage() }
            }
        } else {
            loadingRow {
                // Changing the ID will force it to rebind when new data is loaded even if it is
                // still on screen which will ensure that we trigger loading again.
                id("loading${state.repos.size}")
                onBind { _, _, _ -> viewModel.fetchNextPage() }
            }
        }
    }
}