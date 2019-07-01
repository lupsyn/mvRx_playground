package com.playground.base

import androidx.annotation.CallSuper
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.playground.data.db.MvRxDb
import com.playground.utils.Given
import com.playground.utils.SystemUtils
import com.playground.utils.Then
import com.playground.utils.When
import com.playground.utils.mockwebserver.MockWebServerRule
import com.playground.utils.rules.ClearAppDataRule
import com.playground.utils.rules.RxSchedulersRule
import com.playground.utils.rules.SyncroniseRxJavaRule
import org.junit.AfterClass
import org.junit.Before
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.android.ext.android.inject

/**
 * Base class for all UI tests.
 *
 * Prepare the app to run BDD-style tests and handle configuration needed before running each test.
 */

@RunWith(AndroidJUnit4::class)
abstract class BaseInstrumentationTestCase {

    @Rule
    @JvmField
    val rules: TestRule = getRule()

    lateinit var given: Given
    lateinit var `when`: When
    lateinit var then: Then

    private val applicationContext = getApplicationContext<MvRxTestApplication>()
    private val db: MvRxDb  by applicationContext.inject()

    @Before
    @CallSuper
    open fun setUp() {
        given = Given(serverRule, db)
        `when` = When()
        then = Then()
    }

    companion object {
        private val serverRule = MockWebServerRule()

        @AfterClass
        @JvmStatic
        fun afterClassTearDownInstances() {
            serverRule.shutdownServer()
        }
    }

    private fun getRule(): RuleChain {
        return if (!SystemUtils.isART()) {
            RuleChain
                .outerRule(ClearAppDataRule())
                .around(serverRule)
                .around(RxSchedulersRule())
        } else {
            RuleChain
                .outerRule(ClearAppDataRule())
                .around(serverRule)
                .around(SyncroniseRxJavaRule())
        }
    }
}