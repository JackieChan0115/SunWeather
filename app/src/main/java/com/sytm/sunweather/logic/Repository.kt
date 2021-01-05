package com.sytm.sunweather.logic

import androidx.lifecycle.liveData
import com.sytm.sunweather.logic.dao.PlaceDao
import com.sytm.sunweather.logic.model.Place
import com.sytm.sunweather.logic.network.SunWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import com.sytm.sunweather.logic.model.PlaceResponse
import com.sytm.sunweather.logic.model.Weather
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {

    fun savePlace(place: Place) = PlaceDao.savePlace(place)
    fun getSavePlace() = PlaceDao.getSavedPlace()
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO){
        coroutineScope {
            val deferredRealtime = async {
                SunWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()

            if(realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            }
            else{
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                ", daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }


    fun searchPlaces(query: String) = fire(Dispatchers.IO){

        val placeResponse: PlaceResponse = SunWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok"){
            val places = placeResponse.places
            Result.success(places)
        }
        else{
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            }
            catch (e: Exception){
                Result.failure<T>(e)
            }
            emit(result)
        }
}