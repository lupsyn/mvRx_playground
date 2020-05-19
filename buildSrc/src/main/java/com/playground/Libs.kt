package com.playground

object Libs {
    val androidGradlePlugin = "com.android.tools.build:gradle:3.6.0"
    val junit = "junit:junit:4.12"
    val robolectric = "org.robolectric:robolectric:4.2.1"

    object MvRx {
        private const val mvRxVersion = "1.5.0"
        val core = "com.airbnb.android:mvrx:$mvRxVersion"
        val test = "com.airbnb.android:mvrx-testing:$mvRxVersion"
    }

    object Mockito {
        private const val mockitoVersion = "3.3.3"
        val core = "org.mockito:mockito-core:${mockitoVersion}"
    }

    object Google {
        private const val gsonVersion = "2.8.6"
        private const val materialVersion = "1.2.0-alpha06"

        val material = "com.google.android.material:material:$materialVersion"
        val gson = "com.google.code.gson:gson:$gsonVersion"
    }

    object Kotlin {
        private const val version = "1.3.41"
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.3.6"
        val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        val rx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:$version"
        val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object AndroidX {
        val appcompat = "androidx.appcompat:appcompat:1.1.0"
        val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"

        object Navigation {
            private const val version = "2.2.0"
            val runtime = "androidx.navigation:navigation-runtime-ktx:$version"
            val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            val ui = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Fragment {
            private const val version = "1.2.4"
            val fragment = "androidx.fragment:fragment:$version"
            val fragmentKtx = "androidx.fragment:fragment-ktx:$version"
        }

        object Test {
            private const val espressoVersion = "3.2.0"
            private const val androidTestVersion = "1.2.0"
            private const val junitAssertionsVersion ="1.1.0"
            private const val thruthVersions="1.1.0"
            private const val archCoreVersion="2.1.0"

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

        val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta6"

        val coreKtx = "androidx.core:core-ktx:1.2.0"

        object Lifecycle {
            private const val version = "2.2.0"
            val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
        }

        object Room {
            private const val version = "2.2.5"
            val common = "androidx.room:room-common:$version"
            val runtime = "androidx.room:room-runtime:$version"
            val rxjava2 = "androidx.room:room-rxjava2:$version"
            val compiler = "androidx.room:room-compiler:$version"
            val test = "androidx.room:room-testing:$version"
        }
    }
    object MockWebServer {
        private const val version = "4.7.1"

        val webServer ="com.squareup.okhttp3:mockwebserver:$version"
    }

    object RxJava {
        val rxJava = "io.reactivex.rxjava2:rxjava:2.2.19"
        val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0"
        val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    }

    object Retrofit {
        private const val version = "2.8.2"
        val retrofit = "com.squareup.retrofit2:retrofit:$version"
        val retrofit_rxjava_adapter = "com.squareup.retrofit2:adapter-rxjava2:$version"
        val moshiConverter = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object Moshi {
        private const val version = "1.9.2"
        val moshi = "com.squareup.moshi:moshi:$version"
        val moshi_kotlin = "com.squareup.moshi:moshi-kotlin:$version"
        val moshi_kotlin_code_gen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
    }

    object OkHttp {
        val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:3.13.1"
    }

    object Epoxy {
        private const val version = "3.10.0"
        val epoxy = "com.airbnb.android:epoxy:$version"
        val paging = "com.airbnb.android:epoxy-paging:$version"
        val dataBinding = "com.airbnb.android:epoxy-databinding:$version"
        val processor = "com.airbnb.android:epoxy-processor:$version"
    }

    object Koin {
        private const val version = "2.1.5"
        val koin = "org.koin:koin-android:$version"
        val koinViewModel = "org.koin:koin-androidx-viewmodel:$version"
        val koinScope = "org.koin:koin-androidx-scope:$version"
    }
}
