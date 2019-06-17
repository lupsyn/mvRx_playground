package com.playground.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface GitHubResponsesDao {

    @Query("SELECT * FROM  ${GitHubResponsesEntity.GIT_HUB_RESPONSES}  WHERE request_hash_code LIKE :hashCode")
    fun getResponseEntity(hashCode: Int): Single<GitHubResponsesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveResponseEntity(entity: GitHubResponsesEntity)

    @Query("DELETE FROM ${GitHubResponsesEntity.GIT_HUB_RESPONSES}")
    fun clearTable()
}