package com.jhiltunen.sensorlympics.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(score: Score)
    @Query("select * from Score order by game")
    fun getAll(): LiveData<List<Score>>
}