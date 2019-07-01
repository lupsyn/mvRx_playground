package com.playground.utils.actions

import androidx.test.core.app.ActivityScenario
import com.playground.MainActivity
import com.playground.utils.SystemUtils


class UserActions {

    val onRepositoriesListScreenActions = RepositoriesListScreenActions()

    var onActivity: MainActivity? = null


    fun launchesTheApp() {

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        
        activityScenario.onActivity { onActivity = it }


        /*
        TODO need to synchornise loading the Google Advertising ID with Espresso.
        Easiest way is to wrap it as an Rx operation and all done.
        For now, just use a good old sleep :(
         */
        try {
            Thread.sleep(1000)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    fun pressesBack() {
        if (SystemUtils.isART()) {
            onActivity?.runOnUiThread {
                onActivity?.onBackPressed()
            }
        } else {
            onActivity?.onBackPressed()
        }
    }

    fun onRotation() {
        /**
         * Cause this Activity to be recreated with a new instance.  This results
         * in essentially the same flow as when the Activity is created due to
         * a configuration change -- the current instance will go through its
         * lifecycle to {@link #onDestroy} and a new instance then created after it.
         */
        if (SystemUtils.isART()) {
            onActivity?.runOnUiThread {
                onActivity?.recreate()
            }
        } else {
            onActivity?.recreate()
        }
    }

}