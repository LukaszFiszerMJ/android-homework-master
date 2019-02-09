package com.infullmobile.androidhomework.repository.network.model

import com.google.gson.annotations.SerializedName

data class Forecast(@SerializedName("dt") var  date: Long,
                    @SerializedName("main") var params: WeatherParameters,
                    @SerializedName("weather") var description : List<WeatherDescription>)
