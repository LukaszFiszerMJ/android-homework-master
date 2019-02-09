package com.infullmobile.androidhomework.domain.use_cases

import com.infullmobile.androidhomework.domain.repository.ForecastRepository
import com.infullmobile.androidhomework.domain.model.WeatherDescriptionItem
import io.reactivex.Scheduler
import io.reactivex.Single

open class GetWeatherForCityUseCase (
        private val weatherService: ForecastRepository,
        private val subscribeScheduler: Scheduler,
        private val observeScheduler: Scheduler
    ) {

    open fun getForecast(cityName: String): Single<WeatherDescriptionItem> =
                weatherService.getWeatherForCity(cityName)
                        .subscribeOn(subscribeScheduler)
                        .observeOn(observeScheduler)
    }