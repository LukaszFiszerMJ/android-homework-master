package com.infullmobile.androidhomework.presentation

import com.infullmobile.androidhomework.domain.use_cases.GetWeatherForCityUseCase
import com.infullmobile.androidhomework.domain.use_cases.GetWeatherHourlyForCityUseCase

open class WeatherModel(private val getWeatherHourlyForCityUseCase: GetWeatherHourlyForCityUseCase,
                        private val getWeatherForCityUseCase: GetWeatherForCityUseCase

) {

    open fun getWeatherForecastForCity(cityName: String) =
                    getWeatherHourlyForCityUseCase.getForecast(cityName)

    open fun getWeatherForCity(cityName: String) =
            getWeatherForCityUseCase.getForecast(cityName)
}
