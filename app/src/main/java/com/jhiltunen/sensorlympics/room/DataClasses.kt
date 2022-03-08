package com.jhiltunen.sensorlympics.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Score(
    @PrimaryKey(autoGenerate = true)
    val uid: Long,
    val game: String,
    val score: Long,
    )
{
    //constructor, getter and setter are implicit :)
    //override fun toString() = "$firstname $lastname ($uid)"
}