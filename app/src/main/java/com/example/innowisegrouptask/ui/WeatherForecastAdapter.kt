package com.example.innowisegrouptask.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.innowisegrouptask.R
import com.example.innowisegrouptask.ui.data.OneCallWeatherUi
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class WeatherForecastAdapter() :
    RecyclerView.Adapter<WeatherForecastAdapter.ItemViewHolder>() {
    private val weekDaySimpleDateFormat by lazy {
        SimpleDateFormat("EEEE",Locale.ENGLISH)
    }
    private val weatherList = mutableListOf<OneCallWeatherUi>()
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            1
        } else {
            if (weekDaySimpleDateFormat.format(Date(weatherList[position].time)) == weekDaySimpleDateFormat.format(
                    Date(weatherList[position - 1].time)
                )
            ) {
                2
            } else {
                3
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        if (viewType == 1) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.day_of_week_item, parent, false)
            return ItemViewHolder(view, null)
        } else if (viewType == 2) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.hourly_weather_item, parent, false)
            return ItemViewHolder(view, false)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.day_of_week_item, parent, false)
            return ItemViewHolder(view, true)
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int = weatherList.size

    fun setList(weatherList: List<OneCallWeatherUi>) {
        this.weatherList.apply {
            clear()
            addAll(weatherList)
        }
        notifyDataSetChanged()
    }

    class ItemViewHolder(private val itemView: View, private val isWeekDayChanged: Boolean?) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var weatherImageView: ImageView
        private lateinit var timeTextView: TextView
        private lateinit var mainDescriptionTextView: TextView
        private lateinit var temperatureTextView: TextView
        private lateinit var dayOfWeekTextView: TextView
        private val weekDaySimpleDateFormat by lazy {
            SimpleDateFormat("EEEE", Locale.ENGLISH)
        }
        private val timeSimpleDateFormat by lazy {
            SimpleDateFormat("hh:mm",Locale.ENGLISH)
        }

        fun bind(oneCallWeatherUi: OneCallWeatherUi) {
            initMainViews()
            when (isWeekDayChanged) {
                true -> bindWeekDayChanged(oneCallWeatherUi)
                false -> bindWeekDayNotChanged(oneCallWeatherUi)
                null -> bindFirstElement(oneCallWeatherUi)
            }

        }

        private fun bindFirstElement(oneCallWeatherUi: OneCallWeatherUi) {
            dayOfWeekTextView = itemView.findViewById(R.id.dayOfWeekTextView)
            dayOfWeekTextView.text = "Today"
            setMainViews(oneCallWeatherUi)
        }

        private fun bindWeekDayNotChanged(oneCallWeatherUi: OneCallWeatherUi) {
            setMainViews(oneCallWeatherUi)
        }

        private fun bindWeekDayChanged(oneCallWeatherUi: OneCallWeatherUi) {
            dayOfWeekTextView = itemView.findViewById(R.id.dayOfWeekTextView)
            dayOfWeekTextView.text = weekDaySimpleDateFormat.format(Date(oneCallWeatherUi.time))
            setMainViews(oneCallWeatherUi)
        }

        private fun setMainViews(oneCallWeatherUi: OneCallWeatherUi) {
            when (oneCallWeatherUi.mainDescription) {
                "Clouds" -> weatherImageView.setImageResource(R.drawable.ic_cloud)
                "Clear" -> weatherImageView.setImageResource(R.drawable.ic_baseline_wb_sunny_24)
                "Snow" -> weatherImageView.setImageResource(R.drawable.ic_snowflake)
                "Rain" -> weatherImageView.setImageResource(R.drawable.ic_rainy)
                "Drizzle" -> weatherImageView.setImageResource(R.drawable.ic_drizzle)
                "Thunderstorm" -> weatherImageView.setImageResource(R.drawable.ic_thunderstorm)
                "Fog" -> weatherImageView.setImageResource(R.drawable.ic_cloud)
            }
            timeTextView.text = timeSimpleDateFormat.format(Date(oneCallWeatherUi.time))
            mainDescriptionTextView.text = oneCallWeatherUi.mainDescription
            temperatureTextView.text = "${oneCallWeatherUi.temperature}°C"
        }

        private fun initMainViews() {
            weatherImageView = itemView.findViewById(R.id.weatherImageView)
            timeTextView = itemView.findViewById(R.id.timeTextView)
            mainDescriptionTextView = itemView.findViewById(R.id.mainDescriptionTextView)
            temperatureTextView = itemView.findViewById(R.id.temperatureTextView)
        }
    }
}