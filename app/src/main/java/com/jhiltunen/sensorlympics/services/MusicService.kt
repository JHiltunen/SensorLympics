package com.jhiltunen.sensorlympics.services


import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.Nullable
import com.jhiltunen.sensorlympics.R


class MusicService : Service() {
    var myPlayer: MediaPlayer? = null
    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show()
        myPlayer = MediaPlayer.create(this, R.raw.porilaisten)

        myPlayer!!.isLooping = false // Set looping
    }

    override fun onStart(intent: Intent?, startid: Int) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show()
        myPlayer!!.start()
    }

    override fun onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show()
        myPlayer!!.stop()
    }
}

