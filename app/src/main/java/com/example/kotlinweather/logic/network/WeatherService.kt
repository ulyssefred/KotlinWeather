package com.example.kotlinweather.logic.network

import com.example.kotlinweather.KotlinWeatherApplication
import com.example.kotlinweather.logic.model.DailyResponse
import com.example.kotlinweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    @GET("v2.5/${KotlinWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng")lng:String,@Path("lat")lat:String):
            Call<RealtimeResponse>
    @GET("v2.5/${KotlinWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng")lng:String,@Path("lat")lat:String):
            Call<DailyResponse>
}