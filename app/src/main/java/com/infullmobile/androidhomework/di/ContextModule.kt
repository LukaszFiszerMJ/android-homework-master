package com.infullmobile.androidhomework.di

import android.content.Context
import dagger.Module
import dagger.Provides


@Module
class ContextModule(private var context: Context) {

    @Provides
    @WeatherScope
    fun context(): Context {
        return context.applicationContext
    }
}