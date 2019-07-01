package com.playground.base

import android.app.Application
import androidx.annotation.CallSuper
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = MvRxTestApplication::class)
abstract class RobolectricTestBase {

    private var app: Application? = null

    @Before
    @CallSuper
    open fun setUp() {
        MockitoAnnotations.initMocks(this)
        app = ApplicationProvider.getApplicationContext()
    }
}