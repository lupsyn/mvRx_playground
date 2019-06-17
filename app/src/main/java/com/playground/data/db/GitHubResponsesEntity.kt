package com.playground.data.db

import androidx.room.*
import com.playground.data.db.GitHubResponsesEntity.Companion.GIT_HUB_RESPONSES
import com.playground.data.db.GitHubResponsesEntity.Companion.REQUEST_HASH_CODE
import com.playground.data.db.converters.GitRepoResponseConverter
import com.playground.models.GitRepo

@Entity(
    tableName = GIT_HUB_RESPONSES,
    indices = [(Index(value = [REQUEST_HASH_CODE], unique = true))]
)
@TypeConverters(GitRepoResponseConverter::class)
data class GitHubResponsesEntity constructor(
    @ColumnInfo(name = "response") var response: List<GitRepo>?
) {

    @ColumnInfo(name = ID)
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = TIME_STAMP)
    var timeStamp: Long = 0

    @ColumnInfo(name = REQUEST_HASH_CODE)
    var request_hash_code: Int = -1

    @Ignore
    constructor() : this(null)

    companion object {
        const val ID = "_id"
        const val GIT_HUB_RESPONSES = "git_hub_response"
        const val TIME_STAMP = "time_stamp"
        const val REQUEST_HASH_CODE = "request_hash_code"
    }
}

