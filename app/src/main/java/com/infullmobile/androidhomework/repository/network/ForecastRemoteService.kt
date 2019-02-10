package com.infullmobile.androidhomework.repository.network

import com.infullmobile.androidhomework.repository.network.model.Forecast
import com.infullmobile.androidhomework.repository.network.model.WeatherForecastApi
import io.reactivex.Observable
import retrofit2.Response


import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastRemoteService {

    @GET("forecast")
    fun getForecastForTheCity(@Query("q")city: String): Observable<WeatherForecastApi>

    @GET("weather")
    fun getWeatherForTheCity(@Query("q")city: String): Observable<Forecast>
}