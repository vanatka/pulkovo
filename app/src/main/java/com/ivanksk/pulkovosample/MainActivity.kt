package com.ivanksk.pulkovosample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import com.google.gson.Gson
import com.ivanksk.pulkovo.operations.measureMethod
import com.ivanksk.pulkovo.operations.measureMethodWithLabel
import com.ivanksk.pulkovo.operations.stopLabelMeasure
import com.ivanksk.pulkovo.startMeasure
import com.ivanksk.pulkovo.stopMeasure
import com.ivanksk.pulkovosample.model.CityWeather
import com.ivanksk.pulkovosample.network.weatherAPIClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {

    object Metrics {

        val LoadWeather = "Load weather"

        val DisplayWeather = "Display weather"

    }

    // here we measure how much time it will take to create activity
    // to setup layout, to setup click listeners and etc
    override fun onCreate(savedInstanceState: Bundle?) = measureMethod(this) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupClickListeners()
    }


    private fun loadWeather() {
        // here we measure how much time will take data-load for RxJava chain
        startMeasure(Metrics.LoadWeather,
            weatherAPIClient.getWeather("Tallinn", "476f28ed531b9477e89ddb6ab463dbd5"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .stopMeasure(Metrics.LoadWeather)
            .subscribe ({
                displayWeather(it)
            }, { Log.wtf(">>>", "oh my posh", it )})
    }

    // here we measure how much time it will take
    // to display data on UI, to set text to textviews and etc
    private fun displayWeather(cityWeather: CityWeather) = measureMethodWithLabel(Metrics.DisplayWeather) {
        cityName.text = "Tallinn"
        humidity.text = "Humidity " + "%.2f".format(cityWeather.main!!.humidity)
        temperature.text = "Temperature " + "%.2f".format((cityWeather.main!!.temp - 271.15))
        description.text = "In general it's " + (cityWeather.weather!![0].description)
    }


    override fun onStart() = measureMethodWithLabel("onStart method") {
        super.onStart()
    }

    //
    private fun setupClickListeners() {
        logcatRecords.setOnClickListener {
            // showcase purposes only, any DI tool might be used instead
            Thread {
                val stats = (application as? App)?.statsReducer
                Log.e(">>>", "********************************************************************************")
                Log.e(">>>", "All records")
                stats!!.allRecords().forEach {
                    Log.e(">>>", "$it")
                }
                Log.e(">>>", "********************************************************************************")
                Log.e(">>>", "All Medians")
                stats.allMedians().forEach {
                    Log.e(">>>", "$it")
                }
                Log.e(">>>", "********************************************************************************")
                Log.e(">>>", "All Averages")
                stats.allAverage().forEach {
                    Log.e(">>>", "$it")
                }
                Log.e(">>>", "********************************************************************************")

            }.start()
        }

        clearMeasurements.setOnClickListener {
            Thread {
                (application as? App)?.statsReducer?.clear()
            }.start()
        }

        loadWeather.setOnClickListener {
            loadWeather()
        }
    }
}
