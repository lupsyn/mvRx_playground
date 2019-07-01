package com.playground.utils.rules

import androidx.test.core.app.ApplicationProvider
import com.playground.base.MvRxTestApplication
import com.playground.data.db.MvRxDb
import org.junit.rules.ExternalResource
import org.koin.android.ext.android.inject

/**
 * Clear all application data here.
 * Done to ensure all UI tests start from the same clean state.
 */

class ClearAppDataRule : ExternalResource() {

    private val db: MvRxDb  by ApplicationProvider.getApplicationContext<MvRxTestApplication>().inject()

    override fun before() {
        super.before()
        db.clearAllTables()
    }
}