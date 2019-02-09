package com.infullmobile.androidhomework.presentation

import android.net.Uri
import android.os.Bundle
import com.infullmobile.android.infullmvp.Presenter
import com.infullmobile.androidhomework.addToComposite
import com.infullmobile.androidhomework.domain.model.WeatherDescriptionItem
import com.infullmobile.androidhomework.domain.model.WeatherForecastHourly
import com.infullmobile.androidhomework.repository.network.exception.ServiceException
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import java.io.IOException

enum class WeatherError {
     NO_CONNECTION, UNKNOWN
}

open class WeatherPresenter(
        private val model: WeatherModel,
        view: WeatherView
) : Presenter<WeatherView>(view) {
    private val disposable  =  CompositeDisposable()

    private var weatherForecastCached: WeatherForecastHourly? = null
    var weatherCached : WeatherDescriptionItem? = null

    override fun bind(intentBundle: Bundle, savedInstanceState: Bundle, intentData: Uri?) {

        weatherForecastCached =  savedInstanceState.get(CACHED_FORECAST_KEY) as? WeatherForecastHourly
        weatherCached = savedInstanceState.get(CACHED_WEATHER_KEY) as? WeatherDescriptionItem

        weatherForecastCached?.let { weatherForecast->
            weatherCached?.let { weather->
                presentedView
                        .displayForecast(Pair(weatherForecast, weather))
            }
        }
    }

    override fun saveInstanceState(outState: Bundle) {
        weatherForecastCached?.let {
            outState.putSerializable(CACHED_FORECAST_KEY, weatherForecastCached)
        }
        weatherCached?.let{ weatherCached->
            outState.putSerializable(CACHED_WEATHER_KEY, weatherCached)
        }
    }

    override fun unbind() {
        super.unbind()
        disposable.clear()
    }

    fun getForecast(cityName : String){
        presentedView.showProgress()
        model.getWeatherForecastForCity(cityName)
                .zipWith(
                model.getWeatherForCity(cityName),
                BiFunction<WeatherForecastHourly, WeatherDescriptionItem,
                        Pair<WeatherForecastHourly, WeatherDescriptionItem>>
                { weatherForecast, weather -> Pair(weatherForecast, weather)}
        )
                .subscribe(
                        { weatherForecast ->
                            weatherForecastCached = weatherForecast.first
                            weatherCached = weatherForecast.second
                            presentedView.hideProgress()
                            presentedView.displayForecast(Pair(weatherForecast.first,
                                    weatherForecast.second))
                        },
                        { err->
                            presentedView.hideProgress()
                            onError(err)
                        }
                ).addToComposite(disposable)

    }

    private fun onError(throwable: Throwable) {
        when (throwable) {
            is ServiceException -> {
                presentedView.showErrorMessage(throwable.message ?: "Custom error")
            }
            is IOException -> {
                presentedView.showErrorMessage(WeatherError.NO_CONNECTION)
            }
            else -> presentedView.showErrorMessage(WeatherError.UNKNOWN)
        }
    }
    companion object {
        const val CACHED_FORECAST_KEY  = "cachedForecasts"
        const val CACHED_WEATHER_KEY  = "cachedWeather"
    }
}
