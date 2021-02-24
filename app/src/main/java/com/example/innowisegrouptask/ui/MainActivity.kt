package com.example.innowisegrouptask.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import com.example.innowisegrouptask.R
import com.example.innowisegrouptask.ui.dialogs.LoadingDialog
import com.example.innowisegrouptask.ui.dialogs.LoadingDialogImpl
import com.example.innowisegrouptask.ui.listeners.ShowDismissLoadingListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), LocationListener,
    BottomNavigationView.OnNavigationItemSelectedListener, ShowDismissLoadingListener {
    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialogImpl(this)
    }
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        bottomNavigationView = findViewById(R.id.weatherBottomNavBar)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        fetchCurrentLocation()
        setActionBarTitle("Today")

    }

    override fun setActionBar(toolbar: Toolbar?) {
        super.setActionBar(toolbar)
        toolbar?.title = "Today"
        toolbar?.setTitleTextColor(Color.BLACK)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun startMainFragment(coordinates: Pair<Double, Double>) {
        val bundle = Bundle()
        bundle.putDouble("lat", coordinates.first)
        bundle.putDouble("lon", coordinates.second)
        val myFragment = TodayWeatherFragment()
        myFragment.arguments = bundle
        showLoadingDialog()
        supportFragmentManager.beginTransaction().add(R.id.fragmentFrameLayout, myFragment).commit()
    }

    private fun openForecastFragment(coordinates: Pair<Double, Double>) {
        showLoadingDialog()
        setActionBarTitle(
            getSharedPreferences("curLocation", MODE_PRIVATE).getString(
                "curLocation",
                null
            )
        )
        val bundle = Bundle()
        bundle.putDouble("lat", coordinates.first)
        bundle.putDouble("lon", coordinates.second)
        val myFragment = ForecastFragment()
        myFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentFrameLayout, myFragment)
            .commit()
    }

    private fun openMainFragment(coordinates: Pair<Double, Double>) {
        showLoadingDialog()
        setActionBarTitle("Today")
        val bundle = Bundle()
        bundle.putDouble("lat", coordinates.first)
        bundle.putDouble("lon", coordinates.second)
        val myFragment = TodayWeatherFragment()
        myFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentFrameLayout, myFragment)
            .commit()
    }

    private fun fetchCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                10000,
                10.toFloat(),
                this
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.size > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    fetchCurrentLocation()
                }
            }
        }
    }

    override fun onLocationChanged(location: Location) {
        startMainFragment(Pair(location.latitude, location.longitude))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.forecastItem -> getLastKnownLocation()?.let { openForecastFragment(it) }
            R.id.currentItem -> getLastKnownLocation()?.let { openMainFragment(it) }
        }
        return true
    }

    private fun getLastKnownLocation(): Pair<Double, Double>? {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            return if (location != null) {
                Pair(location.latitude, location.longitude)
            } else {
                null
            }
        }
        return null
    }

    override fun showLoadingDialog() {
        loadingDialog.startLoadingDialog()
    }

    override fun dismissLoadingDialog() {
        loadingDialog.dismissLoadingDialog()
    }

    private fun setActionBarTitle(title: String?) {
        if (title != null) {
            supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            val view = LayoutInflater.from(this).inflate(R.layout.custom_actionbar, null)
            view.findViewById<AppCompatTextView>(R.id.appCompatTextView).text = title
            supportActionBar?.customView = view
        }
    }
}