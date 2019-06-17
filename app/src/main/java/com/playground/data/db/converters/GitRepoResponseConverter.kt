package com.playground.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.playground.models.GitRepo
import java.util.*

class GitRepoResponseConverter
{

    private var gson = Gson()

    @TypeConverter
    fun fromGson(data: String?): List<GitRepo> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<GitRepo>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun toGson(someObjects: List<GitRepo>): String {
        return gson.toJson(someObjects)
    }
}