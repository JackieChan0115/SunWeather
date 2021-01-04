package com.sytm.sunweather

import android.app.Application
import android.content.Context

class SunWeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        @SuppressWarnings("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN = "q0J9dVuQuIMc9uek"
    }
}