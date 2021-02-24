package com.example.innowisegrouptask.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.innowisegrouptask.R
import com.example.innowisegrouptask.service.forecastFragmentPresenter.ForecastFragmentPresenter
import com.example.innowisegrouptask.service.forecastFragmentPresenter.ForecastFragmentPresenterImpl
import com.example.innowisegrouptask.ui.data.OneCallWeatherUi
import com.example.innowisegrouptask.ui.listeners.ForecastFragmentListener
import com.example.innowisegrouptask.ui.listeners.ShowDismissLoadingListener

class ForecastFragment() : Fragment(), ForecastFragmentListener {
    private lateinit var recyclerView: RecyclerView
    private val weatherAdapter: WeatherForecastAdapter = WeatherForecastAdapter()
    private val forecastFragmentPresenter: ForecastFragmentPresenter by lazy {
        ForecastFragmentPresenterImpl(this)
    }
    private lateinit var showDismissLoadingListener: ShowDismissLoadingListener
    private lateinit var coordinatesPair: Pair<Double?, Double?>
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
            coordinatesPair = Pair(arguments?.getDouble("lat"), arguments?.getDouble("lon"))
        }
        return inflater.inflate(R.layout.forecast_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        forecastFragmentPresenter.fetchWeatherList(coordinatesPair as Pair<Double, Double>)
    }

    private fun initRecyclerView(view: View) {
        if (this.context != null) {
            recyclerView = view.findViewById(R.id.recyclerView)
            recyclerView.adapter = weatherAdapter
            recyclerView.layoutManager =
                LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        }

    }

    override fun setWeatherList(weatherList: List<OneCallWeatherUi>) {
        weatherAdapter.setList(weatherList)
        showDismissLoadingListener.dismissLoadingDialog()
    }
}