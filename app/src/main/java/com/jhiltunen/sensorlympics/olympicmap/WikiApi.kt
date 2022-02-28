package com.jhiltunen.sensorlympics.olympicmap


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object WikiApi {
    const val URL = "https://en.wikipedia.org/w/"


    object Model {
        data class All (val query: Query)
        data class Query(val searchinfo: SearchInfo)
        data class SearchInfo(val totalhits: Int)
    }

    interface Service {
        //@GET("api.php?action=query&format=json&list=search&srsearch=Barack%20Obama")
        //@GET("api.php?action=query&format=json&list=search&srsearch=Bomb")
        @GET("api.php?action=query&format=json&list=search&")
        suspend fun hitsGetter(@Query("srsearch") action: String): Model.All
        suspend fun hitsGetter(): Model.All
    }

    private val retrofit = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(Service::class.java)!!
}


class WikiRepository() {
    private val call = WikiApi.service
    suspend fun hitCountCheck(name: String) = call.hitsGetter(name)
}

class WikiViewModel() : ViewModel() {
    private val repository: WikiRepository = WikiRepository()
    val changeNotifier = MutableLiveData<Int>()
    fun getHits(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val serverResp = repository.hitCountCheck(name)
            Log.i("Trump", serverResp.toString())
            changeNotifier.postValue(serverResp.query.searchinfo.totalhits)
        }
    }
}