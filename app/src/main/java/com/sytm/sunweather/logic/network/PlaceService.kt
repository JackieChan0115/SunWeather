package com.sytm.sunweather.logic.network

import com.sytm.sunweather.SunWeatherApplication
import com.sytm.sunweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?token=${SunWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String) : Call<PlaceResponse>
}