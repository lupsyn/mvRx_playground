package com.playground.base

import androidx.test.core.app.ApplicationProvider
import com.playground.MvRxApplication
import com.playground.injection.dbModule
import com.playground.network.GitHubService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


class MvRxTestApplication : MvRxApplication() {

    override fun onCreate() {
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext<MvRxTestApplication>())
            modules(listOf(networkTestModule, dbModule))
        }
    }

    private val networkTestModule = module {
        single {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

            val okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_TEST_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

            retrofit.create(GitHubService::class.java)
        }
    }

    companion object {
        const val ORGANIZATION = "square"
        const val REPOS_PER_PAGE = 20
        const val BASE_TEST_URL = "http://localhost:9999"
    }
}