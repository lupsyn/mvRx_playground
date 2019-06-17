package com.playground.models

data class Permissions(
    val admin: Boolean,
    val pull: Boolean,
    val push: Boolean
)