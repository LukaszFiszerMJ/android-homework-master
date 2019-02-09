package com.infullmobile.androidhomework.repository.network.model

import com.google.gson.annotations.SerializedName

data class City(@SerializedName("name") var cityName : String,
                @SerializedName("country") var country : String)