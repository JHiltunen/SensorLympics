package com.jhiltunen.sensorlympics.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = ([Score::class]), version = 1)
abstract class ScoreDB: RoomDatabase() {
    abstract val scoreDao: ScoreDao
    companion object{
        private var sInstance: ScoreDB? = null
        @Synchronized
        fun get(context: Context): ScoreDB {
            if (sInstance == null) {
                sInstance =
                    Room.databaseBuilder(context.applicationContext,
                        ScoreDB::class.java, "scores.db").build()
            }
            return sInstance!!
        }
    }
}