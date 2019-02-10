package com.infullmobile.androidhomework.presentation

import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.*
import com.infullmobile.android.infullmvp.PresentedActivityView
import com.infullmobile.androidhomework.BuildConfig
import com.infullmobile.androidhomework.R
import com.infullmobile.androidhomework.domain.model.WeatherDescriptionItem
import com.infullmobile.androidhomework.domain.model.WeatherForecastHourly
import com.squareup.picasso.Picasso


open class WeatherView : PresentedActivityView<WeatherPresenter>() {

    @LayoutRes override val layoutResId = R.layout.activity_weather
    val loaderView : ProgressBar by bindView(R.id.loader)
    val hourlyRecyclerView : RecyclerView by bindView(R.id.hourly_forecast_recycler)
    val currentWeatherIconView  : ImageView by bindView(R.id.current_weather_icon)
    val currentTemperatureView : TextView by bindView(R.id.current_temperature)
    val currentDescription: TextView by bindView(R.id.current_description)
    val forecastTitler : TextView by  bindView(R.id.forecast_titler)
    val forecastAdapter = ForecastAdapter()

    override fun onViewsBound() {

        with(hourlyRecyclerView){
            adapter = forecastAdapter
            layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false)
        }
    }

    open fun displayForecast(forecast: Pair<WeatherForecastHourly, WeatherDescriptionItem>) {
        setCardsVisible()

        actionBar?.title = "${forecast.first.city?.name},  ${forecast.first.city?.country}"
        (hourlyRecyclerView.adapter as ForecastAdapter).setForecast(forecast.first.forecasts)

        currentTemperatureView.text = context
                .getString(R.string.temperature_formatted, forecast.second.temperature.toString())
        currentDescription.text = forecast.second.description

        forecast.second.description
        Picasso.with(context)
                .load(BuildConfig.IMAGE_URL + "${forecast.second.icon}.png")
                .into(currentWeatherIconView)

    }

    override fun inflateMenu(menu: Menu, menuInflater: MenuInflater) {
        super.inflateMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.main_menu, menu)

        val menuItem = menu.findItem(R.id.search_button)
        val searchMenuItem = menuItem?.actionView

        if (searchMenuItem is SearchView) {
            searchMenuItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    presenter.getForecast(query)
                    menuItem.collapseActionView()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    open fun showErrorMessage(errorMessage: WeatherError){

        val errorTxt = when(errorMessage){
            WeatherError.NO_CONNECTION -> stringFinder(R.string.no_connection_error_message)
            WeatherError.UNKNOWN -> stringFinder(R.string.unknown_error_message)
            WeatherError.NO_CITY_FOUND -> stringFinder(R.string.no_city_error_message)
            WeatherError.INVALID_ACCESS ->  stringFinder(R.string.invalid_token_error_message)
        }

        Toast.makeText(context, errorTxt,
                Toast.LENGTH_LONG).show()
    }

    open fun showProgress(){
        loaderView.visibility = View.VISIBLE
    }

    open fun hideProgress(){
        loaderView.visibility = View.INVISIBLE
    }

    private fun setCardsVisible(){
        forecastTitler.visibility = View.VISIBLE
    }
}
