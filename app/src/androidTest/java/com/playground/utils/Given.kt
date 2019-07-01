package com.playground.utils

import com.playground.data.db.MvRxDb
import com.playground.utils.arrangements.DatabaseArrangements
import com.playground.utils.arrangements.GitHubApiServerArrangements
import com.playground.utils.arrangements.UserArrangements
import com.playground.utils.mockwebserver.MockWebServerRule

class Given(serverRule: MockWebServerRule, db: MvRxDb) {

    val gitHubServer = GitHubApiServerArrangements(serverRule)
    val database = DatabaseArrangements(db)
    val user = UserArrangements()
}