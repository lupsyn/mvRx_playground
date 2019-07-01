package com.playground.utils

import androidx.test.core.app.ApplicationProvider
import com.playground.MvRxApplication
import com.playground.utils.LazyAsset.Companion.lazyAsset
import java.io.BufferedReader
import java.io.InputStream
import kotlin.reflect.KProperty

object MockResponses {
    val errorClientTimeout by lazyAsset("responses/error/error_client_timeout.json")
    val notFoundError by lazyAsset("responses/error/404.json")
    val gitHubListPage1Pagination by lazyAsset("responses/repository_list_page_1_pagination.json")
    val gitHubListPage2Pagination by lazyAsset("responses/repository_list_page_2_pagination.json")

}

/**
 * Custom delegated property that provides the contents of an asset file by the given [assetName].
 *
 * Example usage:
 * `val awesomeObject: String by lazyAsset("awesome_object.json")`
 */
private class LazyAsset(private val assetName: String) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return readAsset(assetName)
    }

    companion object {
        fun lazyAsset(assetName: String) = LazyAsset(assetName)
    }
}

/**
 * Read a file with the given [assetName] from the _assets_ directory.
 *
 * Throws an exception if the file cannot be found or read correctly.
 */
private fun readAsset(assetName: String): String {
    var inputStream: InputStream? = null
    var bufferedReader: BufferedReader? = null
    try {
        inputStream =
            ApplicationProvider.getApplicationContext<MvRxApplication>().classLoader.getResourceAsStream(assetName)
        bufferedReader = inputStream.bufferedReader()
        return bufferedReader.use { it.readText() }
    } catch (e: Exception) {
        throw RuntimeException("cannot load asset $assetName", e)
    } finally {
        inputStream?.close()
        bufferedReader?.close()
    }
}
