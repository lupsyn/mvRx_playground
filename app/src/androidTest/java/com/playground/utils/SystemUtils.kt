package com.playground.utils

import android.app.Activity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage

object SystemUtils {
    fun isART() = System.getProperty("java.runtime.name")?.toLowerCase()?.contains("android") == true

    fun getCurrentActivity(): Activity? {
        val currentActivity = arrayOfNulls<Activity>(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            val allActivities = ActivityLifecycleMonitorRegistry.getInstance()
                    .getActivitiesInStage(Stage.RESUMED)
            if (!allActivities.isEmpty()) {
                currentActivity[0] = allActivities.iterator().next()
            }
        }
        return currentActivity[0]
    }
}