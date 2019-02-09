package com.infullmobile.androidhomework.domain.model

import java.io.Serializable

data class WeatherDescriptionItem(
                            val icon : String = "",
                            val date : Long = 0L,
                            val description: String = "",
                            val humidity : Int = 0,
                            val temperature : Int = 0,
                            val pressure :Int = 0
): Serializable