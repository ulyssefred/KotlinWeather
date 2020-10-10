package com.example.kotlinweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class KotlinWeatherApplication :Application() {
    companion object{
        const val TOKEN = "sR4WIIGFYH0J5YcT"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}