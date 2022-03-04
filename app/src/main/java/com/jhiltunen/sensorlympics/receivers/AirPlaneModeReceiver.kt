package com.jhiltunen.sensorlympics.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.jhiltunen.sensorlympics.R

//adapted from  https://instagram.com/philipplackner_...

class AirPlaneModeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirPlaneModeEnabled = intent?.getBooleanExtra("state", false) ?: return
        if (isAirPlaneModeEnabled) {
            Toast.makeText(context, R.string.airplane_on, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, R.string.airplane_off, Toast.LENGTH_LONG).show()
        }
    }
}