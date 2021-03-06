package com.jhiltunen.sensorlympics.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jhiltunen.sensorlympics.MainActivity
import com.jhiltunen.sensorlympics.room.Score
import com.jhiltunen.sensorlympics.room.ScoreDB
import kotlinx.coroutines.launch

class ScoreViewModel(application: Application) :
    AndroidViewModel(application) {
    private val scoreDB = ScoreDB.get(application)

    fun getAll(): LiveData<List<Score>> =
        scoreDB.scoreDao.getAll()

    fun getGameScore(game: String): LiveData<List<Long>> =
        scoreDB.scoreDao.getGameScore(game)

    fun getHighscore(game: String): LiveData<Long> =
        scoreDB.scoreDao.getHighscore(game)

    fun getAvgScore(game: String): LiveData<Long> =
        scoreDB.scoreDao.getGameAverage(game)

    fun insert(score: Score) {
        viewModelScope.launch {
            scoreDB.scoreDao.insertOrUpdate(score)
        }
    }

}