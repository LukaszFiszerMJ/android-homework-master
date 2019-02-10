package com.infullmobile.androidhomework.repository.network.utils

import com.infullmobile.androidhomework.repository.network.exception.*
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.*

import java.io.IOException
import java.lang.reflect.Type

class RxErrorHandlingCallAdapterFactory(private val errorUtils : ErrorUtils): CallAdapter.Factory() {

    private val original by lazy {
        RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }
    companion object {

        fun create(errorUtils : ErrorUtils) : CallAdapter.Factory = RxErrorHandlingCallAdapterFactory(errorUtils)
        private const val NO_CITY_ERROR_CODE = 404
        private const val INVALID_ACCESS_TOKEN_ERROR_CODE = 401
    }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *> {
        val wrapped = original.get(returnType, annotations, retrofit) as CallAdapter<out Any, *>
        return RxCallAdapterWrapper(wrapped, errorUtils)
    }

    private class RxCallAdapterWrapper<R>(val wrappedCallAdapter: CallAdapter<R, *>,
                                          val errorUtil: ErrorUtils
    ): CallAdapter<R, Observable<R>> {

        override fun responseType(): Type = wrappedCallAdapter.responseType()


        @Suppress("UNCHECKED_CAST")
        override fun adapt(call: Call<R>): Observable<R> {
            val adapted = (wrappedCallAdapter.adapt(call) as Observable<R>)
           return adapted.onErrorResumeNext { throwable: Throwable ->
                Observable.error(toCustomException(throwable))
            }
        }

        private fun toCustomException(throwable: Throwable): Throwable {
            if (throwable is HttpException) {
                val response = throwable.response()

                if (throwable.code() == NO_CITY_ERROR_CODE) {
                    return NoCityFoundException(errorUtil.parseError(response))
                } else if (throwable.code() == INVALID_ACCESS_TOKEN_ERROR_CODE) {
                    return InvalidAccessTokenException(errorUtil.parseError(response))
                }
            }
            if (throwable is IOException) {
                return NoConnectionException()
            }
            return UnknownException(throwable)
        }

    }
}