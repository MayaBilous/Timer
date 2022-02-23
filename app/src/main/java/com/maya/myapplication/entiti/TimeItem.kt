package com.maya.myapplication.entiti

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimeItem(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var name: String,
    var timeFinish: String,
    var switch: Boolean
) {
    constructor(): this(0,"", "", true)
}