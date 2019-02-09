package com.infullmobile.androidhomework.presentation

import com.infullmobile.androidhomework.domain.model.WeatherForecastFactory
import com.infullmobile.androidhomework.domain.use_cases.GetWeatherForCityUseCase
import com.infullmobile.androidhomework.domain.use_cases.GetWeatherHourlyForCityUseCase
import com.infullmobile.androidhomework.presentation.WeatherModel
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito.anyString

class WeatherModelTest {

    private var useCase: GetWeatherHourlyForCityUseCase = mock()
    private var getWeatherUseCase : GetWeatherForCityUseCase = mock()
    private var model = WeatherModel(useCase, getWeatherUseCase)

    @Test
    fun shouldGetWeatherForecastForCity() {
        // given
        whenever(useCase.getForecast(anyString()))
                .thenReturn(Single.just(WeatherForecastFactory.createTestWeatherForecast()))
        // when
        val whatWeGot = model.getWeatherForecastForCity(WeatherForecastFactory.TEST_CITY_NAME).blockingGet()

        // then
        assertThat(whatWeGot).isEqualTo(WeatherForecastFactory.createTestWeatherForecast())
    }
}