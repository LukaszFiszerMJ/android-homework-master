package com.infullmobile.androidhomework.repository.converter

import com.infullmobile.androidhomework.domain.model.*
import com.infullmobile.androidhomework.repository.network.model.Forecast
import com.infullmobile.androidhomework.repository.network.model.WeatherForecastApi

class ForecastModelConverter {

    fun  apiToDomain(weatherForecastApi: WeatherForecastApi): WeatherForecastHourly {
        return WeatherForecastHourly().apply {

            city = City(weatherForecastApi.city.cityName, weatherForecastApi.city.country)
            forecasts = weatherForecastApi.forecast.map { forecast ->
                apiToDomain(forecast)
            }
        }
    }

    fun  apiToDomain(forecast: Forecast): WeatherDescriptionItem {
        return  WeatherDescriptionItem(
                forecast.description[0].icon,
                forecast.date,
                forecast.description[0].description,
                humidity = forecast.params.humidity.toInt(),
                pressure = forecast.params.pressure.toInt(),
                temperature = forecast.params.temp.toInt()
        )

    }


}