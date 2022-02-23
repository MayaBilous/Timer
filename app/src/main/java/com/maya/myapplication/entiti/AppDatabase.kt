package com.maya.myapplication.entiti

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TimeItem::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun TimeListDao(): TimeListDao

}