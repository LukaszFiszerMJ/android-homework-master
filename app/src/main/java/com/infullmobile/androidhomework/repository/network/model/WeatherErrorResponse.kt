package com.infullmobile.androidhomework.repository.network.model

import com.google.gson.annotations.SerializedName

data class WeatherErrorResponse(
        @SerializedName("cod")
        val code: String,
        @SerializedName("message")
        val message: String
)