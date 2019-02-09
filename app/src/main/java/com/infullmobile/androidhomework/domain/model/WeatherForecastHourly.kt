package com.infullmobile.androidhomework.domain.model

import java.io.Serializable

class WeatherForecastHourly : Serializable {
    var city: City? = null
    var forecasts: List<WeatherDescriptionItem> = listOf()
}
