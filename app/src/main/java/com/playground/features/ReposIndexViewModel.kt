package com.playground.features

import com.airbnb.mvrx.*
import com.playground.MvRxApplication.Companion.ORGANIZATION
import com.playground.MvRxApplication.Companion.REPOS_PER_PAGE
import com.playground.core.MvRxViewModel
import com.playground.data.GitHubRepositoryImpl
import com.playground.data.db.MvRxDb
import com.playground.data.db.utils.TimeProvider
import com.playground.models.GitRepo
import com.playground.network.GitHubService
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

data class ReposIndexState(
    val repos: List<GitRepo> = emptyList(),
    val request: Async<List<GitRepo>> = Uninitialized
) : MvRxState

/**
 * initialState *must* be implemented as a constructor parameter.
 */
class ReposIndexViewModel(
    initialState: ReposIndexState,
    private val dataSource: GitHubRepositoryImpl
) : MvRxViewModel<ReposIndexState>(initialState) {

    init {
        fetchNextPage()
    }

    fun fetchNextPage() = withState { state ->
        if (state.request is Loading) return@withState

        dataSource
            .fetchRepositories(
                organization = ORGANIZATION,
                page = state.repos.size / REPOS_PER_PAGE + 1
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
            val db: MvRxDb   by viewModelContext.activity.inject()

            val repository = GitHubRepositoryImpl(
                db.gitHubResponsesDao(),
                service,
                TimeProvider()
            )
            return ReposIndexViewModel(state, repository)
        }
    }
}
