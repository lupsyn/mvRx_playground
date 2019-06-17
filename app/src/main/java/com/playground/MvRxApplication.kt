package com.playground

import android.app.Application
import com.playground.injection.networkModule
import org.koin.core.context.startKoin

class MvRxApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(networkModule)
        }

    }

    companion object {
        const val ORGANIZATION ="square"
        const val REPOS_PER_PAGE = 20
        const val BASE_URL = "https://api.github.com"
    }
}