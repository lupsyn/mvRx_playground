package com.playground.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GitHubResponsesEntity::class], version = 1)
abstract class MvRxDb : RoomDatabase() {

    abstract fun gitHubResponsesDao(): GitHubResponsesDao
}