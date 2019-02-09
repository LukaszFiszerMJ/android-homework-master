package com.infullmobile.androidhomework.domain.repository

import com.infullmobile.androidhomework.domain.model.WeatherDescriptionItem
import com.infullmobile.androidhomework.domain.model.WeatherForecastHourly
import io.reactivex.Single

interface ForecastRepository {

    fun getWeatherForecastForCity(cityName: String): Single<WeatherForecastHourly>

    fun getWeatherForCity(cityName: String): Single<WeatherDescriptionItem>
}
