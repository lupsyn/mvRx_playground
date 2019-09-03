package com.playground.migration

import com.playground.base.BaseMigrationsTest
import com.playground.data.db.GitHubFavouritesEntity
import com.playground.data.db.MIGRATION_1_0_TO_2
import com.playground.data.db.VERSION_1
import com.playground.data.db.VERSION_2
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Test to check migration from room version 1 to room version  2
 */
class Migration1To2Test : BaseMigrationsTest() {

    @Test
    fun migrationShouldAddFavourites() {
        val supportDatabase =
            migrationTestHelper.createDatabase(DB_NAME, VERSION_1)
        assertFalse(
            "Table git hub favourites has not been created", exist(
                supportDatabase,
                GitHubFavouritesEntity.GIT_HUB_FAVOURITES
            )
        )

        val migratedDb = applyMigration(VERSION_2, MIGRATION_1_0_TO_2)
        assertTrue(
            "Table git hub  favourites has been created", exist(
                migratedDb,
                GitHubFavouritesEntity.GIT_HUB_FAVOURITES
            )
        )
    }
}