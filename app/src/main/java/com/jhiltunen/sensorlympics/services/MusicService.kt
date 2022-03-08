package com.jhiltunen.sensorlympics.services


import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import androidx.annotation.Nullable
import com.jhiltunen.sensorlympics.R


class MusicService : Service() {
    private var poriPlayer: MediaPlayer? = null
    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        poriPlayer = MediaPlayer.create(this, R.raw.porilaisten)
        poriPlayer!!.isLooping = true
    }

    override fun onStart(intent: Intent?, startid: Int) {
        //Toast.makeText(this, getString(R.string.service_started), Toast.LENGTH_SHORT).show()
        poriPlayer!!.start()
    }

    override fun onDestroy() {
        //Toast.makeText(this, getString(R.string.service_stopped), Toast.LENGTH_SHORT).show()
        poriPlayer!!.stop()
    }
}

