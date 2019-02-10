package com.infullmobile.androidhomework.repository.network.exception

import com.infullmobile.androidhomework.repository.network.model.WeatherErrorResponse

class NoCityFoundException (val errorResponse: WeatherErrorResponse): RuntimeException()


//private var errorResponse: WeatherErrorResponse? = null
//
//fun ApiUnknownErrorException(throwable: Throwable): ??? {
//    this(throwable, null)
//}
//
//fun ApiUnknownErrorException(errorResponse: ErrorResponse): ??? {
//    super()
//    this.errorResponse = errorResponse
//}
//
//fun ApiUnknownErrorException(throwable: Throwable, errorResponse: ErrorResponse?): ??? {
//    super(throwable)
//    this.errorResponse = errorResponse
//}
//
//fun getErrorResponse(): ErrorResponse? {
//    return errorResponse
//}
//
//override fun toString(): String {
//    return super.toString() + ": " + errorResponse
//}