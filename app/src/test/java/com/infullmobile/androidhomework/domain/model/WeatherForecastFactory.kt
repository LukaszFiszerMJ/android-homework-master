package com.infullmobile.androidhomework.domain.model

import com.infullmobile.androidhomework.repository.network.model.WeatherDescription

object WeatherForecastFactory {

    const val TEST_CITY_NAME = "Warsaw"
    const val TEST_CITY_COUNTRY = "Poland"
    val TEST_WEATHER_CITY = City(TEST_CITY_NAME, TEST_CITY_COUNTRY)

    fun createTestWeatherForecast() : WeatherForecastHourly {
        val weatherForecast  = WeatherForecastHourly()

        weatherForecast.city = TEST_WEATHER_CITY
        weatherForecast.forecasts = listOf(

                WeatherDescriptionItem("", 0, "",0,0,0),
                WeatherDescriptionItem("", 0, "",0,0,0),
                WeatherDescriptionItem("", 0, "",0,0,0)
        )
        return weatherForecast
    }

    fun createCurrentWeather() : WeatherDescriptionItem{
        return WeatherDescriptionItem()
    }

    fun createCurrentWeather(description: String) : WeatherDescriptionItem{
        return WeatherDescriptionItem(description = description)
    }
}