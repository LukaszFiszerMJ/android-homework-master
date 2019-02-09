package com.infullmobile.androidhomework.domain.use_cases

import com.infullmobile.androidhomework.domain.repository.ForecastRepository
import com.infullmobile.androidhomework.domain.model.WeatherForecastFactory
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GetWeatherForCityUseCaseTest {

    private var service: ForecastRepository = mock()
    private var useCase = GetWeatherHourlyForCityUseCase(service, Schedulers.trampoline(), Schedulers.trampoline())

    @Test
    fun shouldGetForecast() {
        // given
        whenever(service.getWeatherForecastForCity(anyString()))
                .thenReturn(Single.just(WeatherForecastFactory.createTestWeatherForecast()))

        // when
        val whatWeGot = useCase.getForecast(WeatherForecastFactory.TEST_CITY_NAME).blockingGet()

        // then
        assertThat(whatWeGot).isEqualToComparingFieldByField(WeatherForecastFactory.createTestWeatherForecast())
    }
}