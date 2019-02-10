package com.infullmobile.androidhomework.presentation

import android.support.v7.widget.SearchView
import com.infullmobile.android.infullmvp.basetest.InFullMvpActivityBaseTest
import com.infullmobile.androidhomework.R
import com.infullmobile.androidhomework.domain.model.WeatherForecastFactory
import com.infullmobile.androidhomework.di.WeatherModule
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

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

    @Test
    fun shouldRunForForecast() {

        //when
        (testedView.menuItem.actionView as SearchView).setQuery("test", true)

        // then
        verify(testedPresenter).getForecast(com.nhaarman.mockito_kotlin.any())
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