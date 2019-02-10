package com.infullmobile.androidhomework.repository.network.utils

import com.google.gson.Gson
import com.infullmobile.androidhomework.repository.network.model.WeatherErrorResponse
import retrofit2.Response


class ErrorUtils (private val gson: Gson){

    fun parseError(response: Response<*>): WeatherErrorResponse {
        return gson.fromJson(response.errorBody()?.charStream(), WeatherErrorResponse::class.java)
    }
}