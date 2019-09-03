package com.playground.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        GitHubResponsesEntity::class,
        GitHubFavouritesEntity::class
    ],
    version = VERSION
)
abstract class MvRxDb : RoomDatabase() {
    abstract fun gitHubResponsesDao(): GitHubResponsesDao
}