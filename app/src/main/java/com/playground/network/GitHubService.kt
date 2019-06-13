package com.playground.network

import com.playground.MvRxApplication
import com.playground.models.GitReposResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    //https://developer.github.com/v3/

    @GET("/{organization}/repos?type=sources")
    fun fetchORgRepositories(
        @Path("organization") organization: String,
        @Query("type") type: String? = null,
        @Query("query") query: String? = null,
        @Query("page") page: Int = 0,
        @Query("per_page") limit: Int = MvRxApplication.REPO_PER_PAGE
    ): Observable<GitReposResponse>

}