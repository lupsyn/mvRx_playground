package com.playground.network

import android.text.format.DateUtils

object CacheExpirationTime {

    private const val SECONDS_IN_MINUTE = 60
    private const val MILLISECONDS_IN_SECOND = 1000
    private const val MINUTE_TO_MILLISECONDS = SECONDS_IN_MINUTE * MILLISECONDS_IN_SECOND

    const val EXPIRY_TIME_STANDARD = (30 * MINUTE_TO_MILLISECONDS).toLong()
}