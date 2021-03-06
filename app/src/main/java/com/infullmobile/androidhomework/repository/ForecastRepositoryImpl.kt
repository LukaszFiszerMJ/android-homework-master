package com.infullmobile.androidhomework.repository

import com.google.gson.Gson
import com.infullmobile.androidhomework.domain.repository.ForecastRepository
import com.infullmobile.androidhomework.domain.model.WeatherDescriptionItem
import com.infullmobile.androidhomework.domain.model.WeatherForecastHourly
import com.infullmobile.androidhomework.repository.converter.ForecastModelConverter
import com.infullmobile.androidhomework.repository.network.ForecastRemoteService
import com.infullmobile.androidhomework.repository.network.model.Forecast
import com.infullmobile.androidhomework.repository.network.model.WeatherErrorResponse
import io.reactivex.Single
import retrofit2.Response

class ForecastRepositoryImpl (
        private val forecastRemoteService: ForecastRemoteService,
        private val forecastModelConverter: ForecastModelConverter,
        private val gson: Gson
): ForecastRepository {

    override fun getWeatherForCity(cityName: String): Single<WeatherDescriptionItem> {
        return forecastRemoteService.getWeatherForTheCity(cityName)
                .map{forecast: Forecast ->
            forecastModelConverter.apiToDomain(forecast)
        }.singleOrError()
    }

    override fun getWeatherForecastForCity(cityName: String): Single<WeatherForecastHourly> {
        return forecastRemoteService.getForecastForTheCity(cityName)
                .map{
            forecastModelConverter.apiToDomain(it)
        } .singleOrError()
    }


}

