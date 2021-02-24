package com.example.innowisegrouptask.ui

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.media.Image
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.innowisegrouptask.R
import com.example.innowisegrouptask.service.currentweatherfragment.TodayWeatherFragmentPresenter
import com.example.innowisegrouptask.service.currentweatherfragment.TodayWeatherFragmentPresenterImpl
import com.example.innowisegrouptask.ui.data.CurrentWeatherUi
import com.example.innowisegrouptask.ui.listeners.ShowDismissLoadingListener
import com.example.innowisegrouptask.ui.listeners.TodayWeatherFragmentListener

class TodayWeatherFragment() : Fragment(), TodayWeatherFragmentListener {
    private lateinit var mainDescriptionImageView: ImageView
    private lateinit var locationTextView: TextView
    private lateinit var weatherTempAndMainDescTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var pressureTextView: TextView
    private lateinit var airSpeedTextView: TextView
    private lateinit var directionTextView: TextView
    private lateinit var showDismissLoadingListener: ShowDismissLoadingListener
    private lateinit var curWeatherMainLayout : ConstraintLayout
    private val todayWeatherFragmentPresenter: TodayWeatherFragmentPresenter by lazy {
        TodayWeatherFragmentPresenterImpl(this)
    }
    private lateinit var locationCoordinates: Pair<Double?, Double?>
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ShowDismissLoadingListener) {
            showDismissLoadingListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (arguments != null) {
            locationCoordinates = Pair(arguments?.getDouble("lat"), arguments?.getDouble("lon"))
        }
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        if(locationCoordinates.first != null && locationCoordinates.second != null) {
            todayWeatherFragmentPresenter.getCurrentWeather(locationCoordinates as Pair<Double, Double>)
        }
    }

    override fun setCurrentWeather(currentWeatherUi: CurrentWeatherUi) {
        setCurrentWeatherToViews(currentWeatherUi)
        activity?.getSharedPreferences("curLocation", Context.MODE_PRIVATE)?.edit()
            ?.putString("curLocation", currentWeatherUi.location)?.apply()
    }

    private fun initViews(view: View) {
        mainDescriptionImageView = view.findViewById(R.id.mainDescriptionImageView)
        locationTextView = view.findViewById(R.id.locationNameTextView)
        weatherTempAndMainDescTextView = view.findViewById(R.id.weatherTempAndMainDesc)
        humidityTextView = view.findViewById(R.id.humidityTextView)
        pressureTextView = view.findViewById(R.id.pressureTextView)
        airSpeedTextView = view.findViewById(R.id.airSpeedTextView)
        directionTextView = view.findViewById(R.id.directionTextView)
        curWeatherMainLayout = view.findViewById(R.id.curWeatherMainLayout)
    }

    private fun setCurrentWeatherToViews(currentWeatherUi: CurrentWeatherUi) {
        when (currentWeatherUi.mainDescription) {
            "Clouds" -> mainDescriptionImageView.setImageResource(R.drawable.ic_cloud)
            "Clear" -> mainDescriptionImageView.setImageResource(R.drawable.ic_baseline_wb_sunny_24)
            "Snow" -> mainDescriptionImageView.setImageResource(R.drawable.ic_snowflake)
            "Rain" -> mainDescriptionImageView.setImageResource(R.drawable.ic_rainy)
            "Drizzle" -> mainDescriptionImageView.setImageResource(R.drawable.ic_drizzle)
            "Thunderstorm" -> mainDescriptionImageView.setImageResource(R.drawable.ic_thunderstorm)
            "Fog" -> mainDescriptionImageView.setImageResource(R.drawable.ic_cloud)
        }
        locationTextView.text = currentWeatherUi.location
        weatherTempAndMainDescTextView.text ="${currentWeatherUi.mainDescription} | ${currentWeatherUi.temperature}°C"
        humidityTextView.text =  "${currentWeatherUi.humidity}%"
        pressureTextView.text =  "${currentWeatherUi.airPressure}hPa"
        airSpeedTextView.text = "${currentWeatherUi.airSpeed}km/h"
        directionTextView.text = currentWeatherUi.airDirection
        showDismissLoadingListener.dismissLoadingDialog()
        curWeatherMainLayout.visibility = ConstraintLayout.VISIBLE
    }

}