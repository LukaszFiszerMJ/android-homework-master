package com.infullmobile.androidhomework.presentation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.infullmobile.androidhomework.BuildConfig
import com.infullmobile.androidhomework.R
import com.infullmobile.androidhomework.domain.model.WeatherDescriptionItem
import com.infullmobile.androidhomework.toDate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.forecast_hour_item.view.*

class ForecastAdapter: RecyclerView.Adapter<ForecastViewHolder>() {

    private val forecasts = mutableListOf<WeatherDescriptionItem>()

    fun setForecast(forecast: List<WeatherDescriptionItem>) {
        this.forecasts.clear()
        this.forecasts.addAll(forecast)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_hour_item, parent, false)
        return ForecastViewHolder(view)
    }

    override fun getItemCount(): Int = forecasts.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {

        with(holder){
            bind(forecasts[position])
        }
    }
}

class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(forecastElement : WeatherDescriptionItem) {

        itemView.date.text = forecastElement.date.toDate()
        itemView.temperature.text = itemView.context
                .getString(R.string.temperature_formatted, forecastElement.temperature.toString())

        Picasso.with(itemView.context)
                .load(BuildConfig.IMAGE_URL + "${forecastElement.icon}.png")
                .into(itemView.weather_icon)
    }
}