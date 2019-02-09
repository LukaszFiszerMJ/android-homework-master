package com.infullmobile.androidhomework

import com.google.gson.Gson
import com.infullmobile.androidhomework.presentation.WeatherError
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*


fun Disposable.addToComposite(composite: CompositeDisposable) {
    composite.add(this)
}

fun Long.toDate(): String{
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date(this * 1000L))
}