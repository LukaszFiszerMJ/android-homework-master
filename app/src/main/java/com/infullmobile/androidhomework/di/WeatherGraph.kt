package com.infullmobile.androidhomework.di

import android.app.Activity
import android.support.annotation.VisibleForTesting

import com.infullmobile.androidhomework.presentation.WeatherActivity

import dagger.Component

class WeatherGraph(activity: Activity){

    private val builder: DaggerWeatherGraph_WeatherComponent.Builder = DaggerWeatherGraph_WeatherComponent
            .builder()
            .weatherModule(WeatherModule())
            .contextModule(ContextModule(activity))


    fun inject(activity: WeatherActivity) {
        builder
                .build()
                .inject(activity)
    }

    @VisibleForTesting
    fun setWeatherModule(module: WeatherModule) {
        builder.weatherModule(module)
    }

    @WeatherScope
    @Component(modules = [WeatherModule::class])
    interface WeatherComponent {
        fun inject(activity: WeatherActivity)
    }
}
