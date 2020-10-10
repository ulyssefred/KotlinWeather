package com.example.kotlinweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.kotlinweather.KotlinWeatherApplication
import com.example.kotlinweather.logic.model.Place
import com.google.gson.Gson

object PlaceDao {
    fun savePlace(place: Place){
        sharePreferences().edit{
            putString("place", Gson().toJson(place))
        }
    }
    fun getSavedPlace():Place{
        val placeJson = sharePreferences().getString("place","")
        return Gson().fromJson(placeJson , Place::class.java)
    }
    fun isPlaceSaved() = sharePreferences().contains("place")
    private fun sharePreferences() = KotlinWeatherApplication.context.getSharedPreferences("kotlin_weather",Context.MODE_PRIVATE)
}