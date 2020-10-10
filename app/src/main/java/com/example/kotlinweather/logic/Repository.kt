package com.example.kotlinweather.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.kotlinweather.logic.dao.PlaceDao
import com.example.kotlinweather.logic.model.Place
import com.example.kotlinweather.logic.model.Weather
import com.example.kotlinweather.logic.network.KotlinWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = KotlinWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }
    fun refreshWeather(lng:String,lat:String) = fire(Dispatchers.IO) {
            coroutineScope {
                val deferredRealtime = async { KotlinWeatherNetwork.getRealtimeWeather(lng,lat) }
                val deferredDailyWeather = async { KotlinWeatherNetwork.getDailyWeather(lng,lat) }
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDailyWeather.await()
                if (realtimeResponse.status =="ok" && dailyResponse.status =="ok"){
                    val weather = Weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
                    Result.success(weather)
                }else{
                    Result.failure(RuntimeException(
                        "realtime response status${realtimeResponse.status}"+"daily response status is${dailyResponse.status}"
                    ))
                }
            }
    }
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
    fun savePlace(place: Place) = PlaceDao.savePlace(place)
    fun getSavedPlace() = PlaceDao.getSavedPlace()
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

}
