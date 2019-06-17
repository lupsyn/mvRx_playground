package com.playground.data

import com.playground.models.GitRepo
import io.reactivex.Single

interface GitHubRepository {

    fun fetchRepositories(
        organization: String, page: Int = 0
    ): Single<List<GitRepo>>

}