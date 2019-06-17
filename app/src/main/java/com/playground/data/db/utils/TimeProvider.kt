package com.playground.data.db.utils

import android.os.SystemClock
import java.util.*

class TimeProvider {
    fun currentTime() = System.currentTimeMillis()
    fun getElapsedRealtime() = SystemClock.elapsedRealtime()
    fun date() = Date()
}