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

    @Query("select score from Score where game = :gameThe")
    fun getGameScore(gameThe: String): LiveData<List<Long>>

    @Query("select AVG(score) from Score where game = :gameThe")
    fun getGameAverage(gameThe: String): LiveData<Long>

    @Query("select * from Score where game = :gameThe")
    fun getGameStats(gameThe: String): LiveData<List<Score>>

    @Query("select MAX(score) from Score where game = :gameThe")
    fun getHighscore(gameThe: String): LiveData<Long>
}