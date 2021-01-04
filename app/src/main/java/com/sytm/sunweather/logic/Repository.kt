package com.sytm.sunweather.logic

import androidx.lifecycle.liveData
import com.sytm.sunweather.logic.model.Place
import com.sytm.sunweather.logic.network.SunWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import com.sytm.sunweather.logic.model.PlaceResponse
import java.lang.RuntimeException

object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO){
        val result = try {
            val placeResponse: PlaceResponse = SunWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok"){
                val places = placeResponse.places
                Result.success(places)
            }
            else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }
        catch (e: Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}