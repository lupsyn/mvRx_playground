package com.playground.data.db

import android.content.Context
import androidx.room.Room
import com.playground.BuildConfig

class MvRxDbManager(context: Context) {

    private val DATABASE_NAME = "room_db"

    val database: MvRxDb

    init {
        database = createDatabase(context)
    }

    companion object {

        lateinit var INSTANCE: MvRxDbManager
            // private setter for the instance
            private set

        fun init(context: Context) {
            INSTANCE = MvRxDbManager(context)
        }
    }

    private fun createDatabase(context: Context): MvRxDb {
        return if (BuildConfig.DEBUG) {
            Room.databaseBuilder(context, MvRxDb::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
        } else {
            return Room.databaseBuilder(context, MvRxDb::class.java, DATABASE_NAME).build()
        }
    }
}