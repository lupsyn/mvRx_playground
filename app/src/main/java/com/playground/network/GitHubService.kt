package com.playground.network

import com.playground.MvRxApplication
import com.playground.models.GitReposResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    @GET("/orgs/{organization}/repos?")
    fun fetchRepositories(
        @Path("organization") organization: String,
        @Query("type") type: String? = null,
        @Query("query") query: String? = null,
        @Query("page") page: Int = 0,
        @Query("per_page") per_page: Int = MvRxApplication.REPOS_PER_PAGE
    ): Observable<List<GitReposResponse>>

}