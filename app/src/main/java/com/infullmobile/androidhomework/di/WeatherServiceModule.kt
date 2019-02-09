package com.infullmobile.androidhomework.di

import com.google.gson.Gson
import com.infullmobile.androidhomework.domain.repository.ForecastRepository
import com.infullmobile.androidhomework.repository.ForecastRepositoryImpl
import com.infullmobile.androidhomework.repository.converter.ForecastModelConverter
import com.infullmobile.androidhomework.repository.network.ForecastRemoteService
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class])
class WeatherServiceModule {

    @WeatherScope
    @Provides
    internal fun provideForecastModelConverter() : ForecastModelConverter =
            ForecastModelConverter()

    @WeatherScope
    @Provides
    internal fun providesWeatherService(forecastRemoteService: ForecastRemoteService,
                                        forecastModelConverter: ForecastModelConverter,
                                        gson: Gson): ForecastRepository =
            ForecastRepositoryImpl(forecastRemoteService, forecastModelConverter, gson)


}
