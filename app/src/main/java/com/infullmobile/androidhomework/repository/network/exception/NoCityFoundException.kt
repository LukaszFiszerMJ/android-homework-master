package com.infullmobile.androidhomework.repository.network.exception

import com.infullmobile.androidhomework.repository.network.model.WeatherErrorResponse

class NoCityFoundException (val errorResponse: WeatherErrorResponse): RuntimeException()
