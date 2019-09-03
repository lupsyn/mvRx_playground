package com.playground.data.db

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface GitHubFavouritesDao {

    @Query("SELECT * FROM  ${GitHubFavouritesEntity.GIT_HUB_FAVOURITES}")
    fun getFavouritesEntity(): Single<GitHubResponsesEntity>


    @Query("DELETE FROM ${GitHubFavouritesEntity.GIT_HUB_FAVOURITES}")
    fun clearTable()
}