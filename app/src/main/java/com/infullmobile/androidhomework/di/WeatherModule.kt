package com.infullmobile.androidhomework.di

import com.infullmobile.androidhomework.domain.use_cases.GetWeatherHourlyForCityUseCase
import com.infullmobile.androidhomework.domain.repository.ForecastRepository
import com.infullmobile.androidhomework.domain.use_cases.GetWeatherForCityUseCase
import com.infullmobile.androidhomework.presentation.WeatherModel
import com.infullmobile.androidhomework.presentation.WeatherPresenter
import com.infullmobile.androidhomework.presentation.WeatherView
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module (includes = [WeatherServiceModule::class])
open class WeatherModule {

    @WeatherScope
    @Provides
    internal fun providesGetWeatherForCityUseCase(weatherService: ForecastRepository) =
            GetWeatherHourlyForCityUseCase(weatherService, Schedulers.io(), AndroidSchedulers.mainThread())

    @WeatherScope
    @Provides
    internal fun providesGetWeatherDailyForCityUseCase(weatherService: ForecastRepository) =
            GetWeatherForCityUseCase(weatherService, Schedulers.io(), AndroidSchedulers.mainThread())

    @WeatherScope
    @Provides
    internal fun providesWeatherView() = WeatherView()

    @WeatherScope
    @Provides
    fun providesWeatherModel(getWeatherForCityUseCase: GetWeatherHourlyForCityUseCase,
                             getWeatherDailyForCityUseCase: GetWeatherForCityUseCase
                             ) =
            WeatherModel(getWeatherForCityUseCase, getWeatherDailyForCityUseCase)

    @WeatherScope
    @Provides
    open fun providesWeatherPresenter(
            model: WeatherModel,
            view: WeatherView
    ) = WeatherPresenter(model, view)
}
