package com.playground.features

import com.airbnb.mvrx.*
import com.playground.MvRxApplication.Companion.ORGANIZATION
import com.playground.MvRxApplication.Companion.REPOS_PER_PAGE
import com.playground.core.MvRxViewModel
import com.playground.models.GitReposResponse
import com.playground.network.GitHubService
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

data class ReposIndexState(
    val repos: List<GitReposResponse> = emptyList(),
    val request: Async<List<GitReposResponse>> = Uninitialized
) : MvRxState

/**
 * initialState *must* be implemented as a constructor parameter.
 */
class ReposIndexViewModel(
    initialState: ReposIndexState,
    private val gitHubService: GitHubService
) : MvRxViewModel<ReposIndexState>(initialState) {

    init {
        fetchNextPage()
    }

    fun fetchNextPage() = withState { state ->
        if (state.request is Loading) return@withState

        gitHubService
            .fetchRepositories(
                organization = ORGANIZATION,
                type = "",
                page = state.repos.size / REPOS_PER_PAGE + 1,
                per_page = REPOS_PER_PAGE
            )
            .subscribeOn(Schedulers.io())
            .execute { copy(request = it, repos = repos + (it.invoke() ?: emptyList())) }
    }

    /**
     * If you implement MvRxViewModelFactory in your companion object, MvRx will use that to create
     * your ViewModel. You can use this to achieve constructor dependency injection with MvRx.
     *
     * @see MvRxViewModelFactory
     */
    companion object : MvRxViewModelFactory<ReposIndexViewModel, ReposIndexState> {

        override fun create(viewModelContext: ViewModelContext, state: ReposIndexState): ReposIndexViewModel {
            val service: GitHubService by viewModelContext.activity.inject()
            return ReposIndexViewModel(state, service)
        }
    }
}
