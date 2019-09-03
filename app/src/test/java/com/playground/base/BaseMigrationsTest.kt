package com.playground.base

import android.database.Cursor
import android.database.sqlite.SQLiteException
import androidx.room.migration.Migration
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.platform.app.InstrumentationRegistry
import com.playground.data.db.MvRxDb
import org.junit.Assert
import org.junit.Rule

abstract class BaseMigrationsTest : RobolectricTestBase() {

    val DB_NAME = "test_room_db"

    @get:Rule
    var migrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        MvRxDb::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    protected fun exist(db: SupportSQLiteDatabase, vararg tables: String): Boolean {
        return try {
            tables.map {
                db.query("SELECT * FROM $it") != null
            }.find { false } ?: true
        } catch (e: SQLiteException) {
            print("Table in $tables does not exist before migration: ${e.localizedMessage}")
            false
        } finally {
            db.close()
        }
    }

    /**
     * This method run the migration and returns a SupportSQLiteDatabase.
     * This object can be used to check the changes in the db after the migration.
     */
    protected fun applyMigration(version: Int, migration: Migration): SupportSQLiteDatabase =
        applyMigrations(version, migration)

    protected fun applyMigrations(
        version: Int,
        vararg migrations: Migration
    ): SupportSQLiteDatabase = migrationTestHelper.runMigrationsAndValidate(
        DB_NAME,
        version,
        true,
        *migrations
    )

    fun assertColumnDoesNotExist(cursor: Cursor, columnName: String, tableName: String = "") {
        Assert.assertFalse(
            "$columnName does NOT exist in table $tableName",
            cursor.columnNames.contains(columnName)
        )
    }

    fun assertColumnExists(cursor: Cursor, columnName: String, tableName: String = "") {
        Assert.assertTrue(
            "$columnName EXIST in $tableName",
            cursor.columnNames.contains(columnName)
        )
    }

    fun verifyColumnWasCreated(
        oldVersion: Int,
        newVersion: Int,
        migration: Migration,
        columnName: String,
        tableName: String
    ) {
        val supportDatabase =
            migrationTestHelper.createDatabase(DB_NAME, oldVersion)
        val cursor = supportDatabase.query("SELECT * FROM $tableName")
        assertColumnDoesNotExist(cursor, columnName, tableName)
        cursor.close()

        val migratedDb = applyMigration(newVersion, migration)
        val cursorForMigratedDb = migratedDb.query("SELECT * FROM $tableName")
        assertColumnExists(cursorForMigratedDb, columnName, tableName)
    }
}