package com.infullmobile.androidhomework.repository.network.model

import com.google.gson.annotations.SerializedName

data class WeatherParameters (
        @SerializedName("temp") var temp: Double,
        @SerializedName("pressure") var pressure: Double,
        @SerializedName("humidity") var humidity: Double
)