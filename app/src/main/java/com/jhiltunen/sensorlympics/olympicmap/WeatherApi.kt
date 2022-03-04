package com.jhiltunen.sensorlympics.olympicmap




import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhiltunen.sensorlympics.olympicmap.WeatherApi.appid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object WeatherApi {
    const val URL = "https://api.openweathermap.org/"
    const val appid = ""

    object Model {
        data class All (val main: Main)
        data class Main (val temp: Double)
    }

    interface Service {
        //@GET("api.php?action=query&format=json&list=search&srsearch=Barack%20Obama")
        //@GET("api.php?action=query&format=json&list=search&srsearch=Bomb")
        @GET("data/2.5/weather?")
        suspend fun hitsGetter(@Query("q") q:  String, @Query("appid") appid: String ): Model.All
        //suspend fun hitsGetter(@Query("lat") lat: Double, @Query("long") lon: Double, @Query("appid") appid: String ): Model.All
        suspend fun hitsGetter(): Model.All
    }

    private val retrofit = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(Service::class.java)!!
}

class WeatherRepository() {
    private val call = WeatherApi.service
    suspend fun hitCountCheck(q: String) = call.hitsGetter(q, appid)
    //suspend fun hitCountCheck(lat: Double, lon: Double) = call.hitsGetter(lat, lon, appid)

}

class WeatherViewModel() : ViewModel() {
    private val repository: WeatherRepository = WeatherRepository()
    val changeNotifier = MutableLiveData<Double>()
    fun getHits(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val serverResp = repository.hitCountCheck(city)
            //Log.i("Trump", serverResp.toString())
            changeNotifier.postValue(serverResp.main.temp)
        }
    }
}

