package com.infullmobile.androidhomework.presentation

import com.infullmobile.android.infullmvp.basetest.InFullMvpActivityBaseTest
import com.infullmobile.androidhomework.domain.model.WeatherForecastFactory
import com.infullmobile.androidhomework.di.WeatherModule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class WeatherViewTest : InFullMvpActivityBaseTest<WeatherActivity, WeatherPresenter, WeatherView>() {

    private val testWeatherForecast = WeatherForecastFactory.createTestWeatherForecast()
    private val testWeather = WeatherForecastFactory.createCurrentWeather()

    @Test
    fun shouldDisplayForecast() {
        // when
        testedView.displayForecast(Pair(testWeatherForecast,testWeather))

        // then
        assertThat(testedView.forecastAdapter.itemCount).isEqualTo(3)
    }

    override fun substituteModules(activity: WeatherActivity) {
        activity.weatherGraph.setWeatherModule(TestWeatherModule())
    }

    override val testActivityClass: Class<WeatherActivity>
        get() = WeatherActivity::class.java

    private inner class TestWeatherModule : WeatherModule() {
        override fun providesWeatherPresenter(model: WeatherModel, view: WeatherView): WeatherPresenter
                = mock(WeatherPresenter::class.java)
    }
}