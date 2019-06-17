package com.playground.utils

import androidx.room.EmptyResultSetException

object TestApiErrors {
    val AN_ERROR = Throwable("something went wrong")

    fun emptyDbError(): Throwable = EmptyResultSetException("Database error message")
}