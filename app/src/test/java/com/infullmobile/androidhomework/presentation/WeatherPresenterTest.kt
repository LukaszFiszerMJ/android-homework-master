package com.infullmobile.androidhomework.presentation

import android.os.Bundle
import com.infullmobile.androidhomework.domain.model.WeatherForecastFactory
import com.infullmobile.androidhomework.presentation.WeatherError
import com.infullmobile.androidhomework.presentation.WeatherModel
import com.infullmobile.androidhomework.presentation.WeatherPresenter
import com.infullmobile.androidhomework.presentation.WeatherPresenter.Companion.CACHED_FORECAST_KEY
import com.infullmobile.androidhomework.presentation.WeatherPresenter.Companion.CACHED_WEATHER_KEY
import com.infullmobile.androidhomework.presentation.WeatherView
import com.infullmobile.androidhomework.repository.network.exception.InvalidAccessTokenException
import com.infullmobile.androidhomework.repository.network.exception.NoConnectionException
import com.infullmobile.androidhomework.repository.network.model.WeatherErrorResponse
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito.*
import java.io.IOException

class WeatherPresenterTest {

    private var model: WeatherModel = mock()
    private var view: WeatherView =  mock()
    private var bundleState: Bundle = mock ()

    private var presenter = WeatherPresenter(model, view)
    private val testWeatherForecast = WeatherForecastFactory.createTestWeatherForecast()
    private val testWeather = WeatherForecastFactory.createCurrentWeather()
    private val iOErrorMessage = WeatherError.NO_CONNECTION
    private val invalidTokenErrorMessage = WeatherError.INVALID_ACCESS


    @Test
    fun shouldShowForecastOnBind() {

        // given
        whenever(bundleState.get(eq(CACHED_FORECAST_KEY))).thenReturn(testWeatherForecast)
        whenever(bundleState.get(eq(CACHED_WEATHER_KEY))).thenReturn(testWeather)

        // when
        presenter.bind(Bundle(), bundleState, null)

        // then
        verify(view).displayForecast(Pair(testWeatherForecast,testWeather))
    }

    @Test
    fun shouldShowForecastOnGetForecast(){
        //given
        whenever(model.getWeatherForecastForCity(anyString())).thenReturn(Single.just(testWeatherForecast))
        whenever(model.getWeatherForCity(anyString())).thenReturn(Single.just(testWeather))

        //when
        presenter.getForecast("")

        // then
        verify(view).displayForecast(Pair(testWeatherForecast, testWeather))
    }

    @Test
    fun shouldShowNoConnectionError(){
        //given
        whenever(model.getWeatherForecastForCity(anyString()))
                .thenReturn(Single.error(NoConnectionException()))
        whenever(model.getWeatherForCity(anyString()))
                .thenReturn(Single.error(NoConnectionException()))

        //when
        presenter.getForecast("")

        // then
        verify(view).showErrorMessage(iOErrorMessage)
    }

    @Test
    fun shouldShowInvalidAccessTokenError(){
        //given
        whenever(model.getWeatherForecastForCity(anyString()))
                .thenReturn(Single.error(InvalidAccessTokenException(WeatherErrorResponse())))
        whenever(model.getWeatherForCity(anyString()))
                .thenReturn(Single.error(InvalidAccessTokenException(WeatherErrorResponse())))

        //when
        presenter.getForecast("")

        // then
        verify(view).showErrorMessage(invalidTokenErrorMessage)
    }
}