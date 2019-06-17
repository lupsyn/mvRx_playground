package com.playground.data

import com.playground.data.db.GitHubResponsesDao
import com.playground.data.db.GitHubResponsesEntity
import com.playground.data.db.utils.TimeProvider
import com.playground.models.GitRepo
import com.playground.network.CacheExpirationTime.EXPIRY_TIME_STANDARD
import com.playground.network.GitHubService
import com.playground.utils.EspressoIdlingResource
import io.reactivex.Single

class GitHubRepositoryImpl(
    private val dao: GitHubResponsesDao,
    private val api: GitHubService,
    private val timeProvider: TimeProvider
) : GitHubRepository {

    override fun fetchRepositories(organization: String, page: Int): Single<List<GitRepo>> {
        return getFromLocalStorage(organization, page)
            .doOnSubscribe { EspressoIdlingResource.increment() }
            .doAfterTerminate { EspressoIdlingResource.decrement() }
            .onErrorResumeNext { getFromNetworkAndSave(organization, page) }
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
        }.onErrorResumeNext {
            Single.error(it)
        }
    }

    private fun getFromLocalStorage(
        organization: String,
        page: Int
    ): Single<List<GitRepo>> {
        val request = GitHubRepositoryRequest(organization, page)
        return dao.getResponseEntity(request.hashCode())
            .filter { isLocalDataWithinExpiryTime(it.timeStamp) }
            .map { it.response ?: emptyList() }
            .toSingle()
    }

    private fun isLocalDataWithinExpiryTime(timeStamp: Long, expiry: Long = EXPIRY_TIME_STANDARD): Boolean =
        System.currentTimeMillis() - timeStamp < expiry

    data class GitHubRepositoryRequest(val organization: String, val page: Int)
}