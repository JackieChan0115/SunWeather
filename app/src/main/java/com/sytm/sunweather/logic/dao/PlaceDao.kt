package com.sytm.sunweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.sytm.sunweather.SunWeatherApplication
import com.sytm.sunweather.logic.model.Place

object PlaceDao {

    fun savePlace(place: Place){
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace() : Place{
        val placeJson = sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = SunWeatherApplication.context.
                                        getSharedPreferences("sun_weather", Context.MODE_PRIVATE)
}