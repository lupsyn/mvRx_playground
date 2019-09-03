package com.playground.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.playground.data.db.GitHubFavouritesEntity.Companion.GIT_HUB_FAVOURITES

const val VERSION_1 = 1
const val VERSION_2 = 2
const val VERSION = VERSION_2


/**
 * This file is resolved statically.
 * It stores the scripts for room migrations.
 *
 */

val MIGRATION_1_0_TO_2: Migration = object : Migration(VERSION_1, VERSION_2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS $GIT_HUB_FAVOURITES (`_id` INTEGER NOT NULL, `favourites` TEXT, PRIMARY KEY(`_id`))")
    }
}