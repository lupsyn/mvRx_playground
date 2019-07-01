package com.playground

import android.app.Application
import com.playground.injection.dbModule
import com.playground.injection.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class MvRxApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MvRxApplication)
            modules(listOf(networkModule, dbModule))
        }
    }

    companion object {
        const val ORGANIZATION = "square"
        const val REPOS_PER_PAGE = 20
        const val BASE_URL = "https://api.github.com"
    }
}