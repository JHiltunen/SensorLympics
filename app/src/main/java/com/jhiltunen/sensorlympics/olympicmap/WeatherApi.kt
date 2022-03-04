package com.jhiltunen.sensorlympics.olympicmap


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
    private const val URL = "https://api.openweathermap.org/"
    const val appid = "4020606b3cef7fbdc80715c2056ad6cf"

    object Model {
        data class All(val main: Main)
        data class Main(val temp: Double)
    }

    interface Service {
        @GET("data/2.5/weather?")
        suspend fun hitsGetter(@Query("q") q: String, @Query("appid") appid: String): Model.All
    }

    private val retrofit = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: Service = retrofit.create(Service::class.java)
}

class WeatherRepository {
    private val call = WeatherApi.service
    suspend fun hitCountCheck(q: String) = call.hitsGetter(q, appid)
}

class WeatherViewModel : ViewModel() {
    private val repository: WeatherRepository = WeatherRepository()
    val changeNotifier = MutableLiveData<Double>()
    fun getHits(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val serverResp = repository.hitCountCheck(city)
            changeNotifier.postValue(serverResp.main.temp)
        }
    }
}

