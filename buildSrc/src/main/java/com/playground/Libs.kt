package com.playground

object Libs {
    val androidGradlePlugin = "com.android.tools.build:gradle:3.4.1"

    val dexcountGradlePlugin = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:0.8.6"


    val rxLint = "nl.littlerobots.rxlint:rxlint:1.7.3"

    val junit = "junit:junit:4.12"

    val robolectric = "org.robolectric:robolectric:4.2"

    val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0"

    object MvRx {
        private const val mvRxVersion = "1.0.0"
        val core = "com.airbnb.android:mvrx:$mvRxVersion"
        val test = "com.airbnb.android:mvrx-testing:$mvRxVersion"
    }

    object Mockito {
        private const val mockitoVersion = "2.24.0"
        val core = "org.mockito:mockito-core:${mockitoVersion}"
    }

    object Google {
        private const val gsonVersion = "2.7"
        val material = "com.google.android.material:material:1.1.0-alpha04"
        val gson = "com.google.code.gson:gson:$gsonVersion"
    }

    object Kotlin {
        private const val version = "1.3.21"
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.1.1"
        val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        val rx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:$version"
        val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object AndroidX {
        val appcompat = "androidx.appcompat:appcompat:1.1.0-alpha03"
        val recyclerview = "androidx.recyclerview:recyclerview:1.0.0"

        object Navigation {
            private const val version = "2.0.0"
            val runtime = "androidx.navigation:navigation-runtime-ktx:$version"
            val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            val ui = "androidx.navigation:navigation-ui-ktx:$version"
            val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        object Fragment {
            private const val version = "1.1.0-alpha03"
            val fragment = "androidx.fragment:fragment:$version"
            val fragmentKtx = "androidx.fragment:fragment-ktx:$version"
        }

        object Test {
            private const val espressoVersion = "3.2.0"
            private const val androidTestVersion = "1.2.0"
            private const val junitAssertionsVersion ="1.1.0"
            private const val thruthVersions="1.1.0"
            private const val archCoreVersion="2.0.0-rc01"

            val core = "androidx.test:core:$androidTestVersion"
            val runner = "androidx.test:runner:$androidTestVersion"
            val rules = "androidx.test:rules:$androidTestVersion"
            var archCore = "androidx.arch.core:core-testing:$archCoreVersion"

            val espressoCore = "androidx.test.espresso:espresso-core:$espressoVersion"
            val espressoContrib= "androidx.test.espresso:espresso-contrib:$espressoVersion"
            val espressoIdlingResources = "androidx.test.espresso:espresso-idling-resource:$espressoVersion"
            val junitAssertions = "androidx.test.ext:junit:$junitAssertionsVersion"
            val thruth = "androidx.test.ext:truth:$thruthVersions"
        }

        val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.0-alpha3"

        val coreKtx = "androidx.core:core-ktx:1.0.1"

        object Lifecycle {
            private const val version = "2.0.0"
            val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
        }

        object Room {
            private const val version = "2.1.0-rc01"
            val common = "androidx.room:room-common:$version"
            val runtime = "androidx.room:room-runtime:$version"
            val rxjava2 = "androidx.room:room-rxjava2:$version"
            val compiler = "androidx.room:room-compiler:$version"
        }
    }
    object MockWebServer {
        private const val version = "3.12.1"

        val webServer ="com.squareup.okhttp3:mockwebserver:3.12.1"
    }



    object RxJava {
        val rxJava = "io.reactivex.rxjava2:rxjava:2.2.6"
        val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.3.0"
        val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    }

    object Glide {
        private const val version = "4.9.0"
        val glide = "com.github.bumptech.glide:glide:$version"
        val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object Retrofit {
        private const val version = "2.5.0"
        val retrofit = "com.squareup.retrofit2:retrofit:$version"
        val retrofit_rxjava_adapter = "com.squareup.retrofit2:adapter-rxjava2:$version"
        val moshiConverter = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object Moshi {
        private const val version = "1.6.0"
        val moshi = "com.squareup.moshi:moshi:$version"
        val moshi_kotlin = "com.squareup.moshi:moshi-kotlin:$version"
        val moshi_kotlin_code_gen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
    }

    object OkHttp {
        val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:3.13.1"
    }

    object Epoxy {
        private const val version = "3.3.0"
        val epoxy = "com.airbnb.android:epoxy:$version"
        val paging = "com.airbnb.android:epoxy-paging:$version"
        val dataBinding = "com.airbnb.android:epoxy-databinding:$version"
        val processor = "com.airbnb.android:epoxy-processor:$version"
    }

    object Koin {
        private const val version = "2.0.1"
        val koin = "org.koin:koin-android:$version"
        val koinViewModel = "org.koin:koin-androidx-viewmodel:$version"
        val koinScope = "org.koin:koin-androidx-scope:$version"
    }
}
