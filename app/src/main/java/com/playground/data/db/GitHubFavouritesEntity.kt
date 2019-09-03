package com.playground.data.db

import androidx.room.*
import com.playground.data.db.GitHubFavouritesEntity.Companion.GIT_HUB_FAVOURITES
import com.playground.data.db.GitHubResponsesEntity.Companion.GIT_HUB_RESPONSES
import com.playground.data.db.GitHubResponsesEntity.Companion.REQUEST_HASH_CODE
import com.playground.data.db.converters.GitRepoResponseConverter
import com.playground.models.GitRepo

@Entity(tableName = GIT_HUB_FAVOURITES)
@TypeConverters(GitRepoResponseConverter::class)
data class GitHubFavouritesEntity constructor(
    @ColumnInfo(name = "favourites") var favourites: List<GitRepo>?
) {

    @ColumnInfo(name = ID)
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @Ignore
    constructor() : this(null)

    companion object {
        const val ID = "_id"
        const val GIT_HUB_FAVOURITES = "git_hub_favourites"
    }
}

