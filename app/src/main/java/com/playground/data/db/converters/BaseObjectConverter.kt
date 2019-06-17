package com.playground.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

abstract class BaseObjectConverter<T> {

    private var gson = Gson()

    @TypeConverter
    fun fromGson(data: String?): List<T> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun toGson(someObjects: List<T>): String {
        return gson.toJson(someObjects)
    }
}