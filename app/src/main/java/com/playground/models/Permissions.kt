package com.playground.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Permissions(
    val admin: Boolean,
    val pull: Boolean,
    val push: Boolean
) : Parcelable