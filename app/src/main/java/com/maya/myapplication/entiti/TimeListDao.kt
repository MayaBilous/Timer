package com.maya.myapplication.entiti

import androidx.room.*

@Dao
interface TimeListDao {
//    @Query("SELECT name FROM timeItem ")
//    fun getName(): List<TimeItem>
//
//
//    @Query("SELECT timeFinish FROM timeItem ")
//    fun getTimeFinish(): List<CustomItem>


    @Query("SELECT * FROM timeItem ")
    fun getAll(): List<TimeItem>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: TimeItem)


    @Delete()
    fun delete(item: TimeItem)


    @Update
    fun updateItems(item: TimeItem)


}