package com.playground.data

import com.playground.data.db.GitHubResponsesDao
import com.playground.data.db.GitHubResponsesEntity
import com.playground.data.db.utils.TimeProvider
import com.playground.models.GitRepo
import com.playground.network.GitHubService
import io.reactivex.Single

class GitHubRepositoryImpl(
    val dao: GitHubResponsesDao,
    val api: GitHubService,
    val timeProvider: TimeProvider
) : GitHubRepository {

    override fun fetchRepositories(organization: String, page: Int): Single<List<GitRepo>> {
        val request = GitHubRepositoryRequest(organization, page)
        return getFromLocalStorage(request.hashCode())
            .onErrorResumeNext {
                getFromNetworkAndSave(organization, page)
            }
    }

    private fun getFromNetworkAndSave(
        organization: String,
        page: Int
    ): Single<List<GitRepo>> {
        return api.fetchRepositories(
            organization = organization,
            page = page
        ).doOnSuccess {
            val request = GitHubRepositoryRequest(organization, page)
            val entityToSave = GitHubResponsesEntity(it).also { entity ->
                entity.request_hash_code = request.hashCode()
                entity.timeStamp = timeProvider.currentTime()
            }
            dao.saveResponseEntity(entityToSave)
        }
    }

    private fun getFromLocalStorage(
        hashCode: Int
    ): Single<List<GitRepo>> {
        return dao.getResponseEntity(hashCode)
            .map {
                it.response ?: emptyList()
            }
    }

    data class GitHubRepositoryRequest(val organization: String, val page: Int)
}