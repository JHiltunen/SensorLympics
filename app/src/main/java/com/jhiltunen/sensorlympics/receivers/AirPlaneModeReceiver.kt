package com.jhiltunen.sensorlympics.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import com.jhiltunen.sensorlympics.MainActivity
import com.jhiltunen.sensorlympics.R

//adapted from  https://instagram.com/philipplackner_...
class AirPlaneModeReceiver : BroadcastReceiver() {

    @OptIn(ExperimentalFoundationApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirPlaneModeEnabled = intent?.getBooleanExtra("state", false) ?: return
        if (isAirPlaneModeEnabled) {
           MainActivity.receiverViewModel.updateAirplane(true)
            Toast.makeText(context, R.string.airplane_on, Toast.LENGTH_LONG).show()
        } else {
            MainActivity.receiverViewModel.updateAirplane(false)
            Toast.makeText(context, R.string.airplane_off, Toast.LENGTH_LONG).show()
        }
    }
}