package com.infullmobile.androidhomework.repository.network.model

import com.google.gson.annotations.SerializedName

data class WeatherForecastApi (@SerializedName("city") var city : City,
                               @SerializedName("list") var forecast : List<Forecast>)