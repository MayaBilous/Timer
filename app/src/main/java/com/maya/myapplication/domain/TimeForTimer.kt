package com.maya.myapplication.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimeForTimer(
    val year: String,
    val month: String,
    val day: String,
    val hour: String,
    val minute: String
) : Parcelable
