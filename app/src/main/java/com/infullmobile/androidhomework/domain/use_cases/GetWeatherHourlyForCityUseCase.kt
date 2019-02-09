package com.infullmobile.androidhomework.domain.use_cases

import com.infullmobile.androidhomework.domain.repository.ForecastRepository
import com.infullmobile.androidhomework.domain.model.WeatherForecastHourly
import io.reactivex.Scheduler
import io.reactivex.Single

open class GetWeatherHourlyForCityUseCase(
        private val weatherService: ForecastRepository,
        private val subscribeScheduler: Scheduler,
        private val observeScheduler: Scheduler
) {

    open fun getForecast(cityName: String): Single<WeatherForecastHourly> =
            weatherService.getWeatherForecastForCity(cityName)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler)

}
