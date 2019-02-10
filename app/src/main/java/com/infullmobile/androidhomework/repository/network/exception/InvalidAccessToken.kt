package com.infullmobile.androidhomework.repository.network.exception

import com.infullmobile.androidhomework.repository.network.model.WeatherErrorResponse

class InvalidAccessTokenException (val errorResponse: WeatherErrorResponse): RuntimeException()