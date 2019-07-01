package com.playground.base

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

@Suppress("unused") // set as a testInstrumentationRunner by full class name
class TestInstrumentationRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, MvRxTestApplication::class.java.name, context)
    }
}