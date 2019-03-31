package com.ivanksk.pulkovosample

import android.app.Application
import android.util.Log
import com.ivanksk.pulkovo.dispatcher.PulkovoDispatcher
import com.ivanksk.pulkovo.operations.IMeasureRecord
import com.ivanksk.pulkovo.operations.stopLabelMeasure
import com.ivanksk.pulkovo.reducer.IReducer
import com.ivanksk.pulkovo.stats.StatsReducer

class App : Application() {

    companion object {

        val WHAT_EVER_YOU_WANT_TAG = "TAG"

    }

    lateinit var statsReducer: StatsReducer

    override fun onCreate() {
        super.onCreate()
        statsReducer = StatsReducer(this)
        val context = this

        PulkovoDispatcher
            // Add logger to make output with measured values
            // this is generic implementation
            .addReducer(object : IReducer {

                override fun reduce(record: IMeasureRecord) {
                    // use what ever logger you want
                    // here standard Android logger is used
                    Log.e(WHAT_EVER_YOU_WANT_TAG, "$record")
                }

            })
            // Add StatsSollector to collect values
            // get
            .addReducer(statsReducer)
    }
}