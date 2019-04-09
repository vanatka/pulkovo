# Pulkovo. Kotlin library to measure method elapsed time.

A kotlin-friendly library in functional programming style with ability to measure elapsed time for:
- methods
- code blocks
- RxJava chains

Also it provides StatsCollector plugin, to store measurements into db, calculate average, median, max and min values. With ability export to CSV file, to make analysis easier.

It has release and debug implementation. 

Sometimes it's necessary measure, how much time it takes to execute some block of code.
In java-android-world there are such solutions like Pury, Meter and etc.
However they are not Kotlin friendly. With Pulkovo it's possible do it in Kotlin way:

```kotlin
  // here we measure method elapsed time, 
  // label will be tracked as MainActivity::onCreate, 
  // class & method name will extracted automatically
  override fun onCreate(savedInstanceState: Bundle?) = measureMethod(this) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setupClickListeners()
  }
  
  // here we measure method elapsed time, 
  // here we use custom label, it will tracked as "onStart method"
  override fun onStart() = measureMethodWithLabel("onStart method") {
    super.onStart()
    //
  }
```

and see output:
```
  MainActivity::onCreate took 129 ms at 1554050670580 with null payload
  MainActivity::onStart took 0 ms at 1554050670583 with null payload
  Display weather took 0 ms at 1554050688450 with null payload
  Load weather took 600 ms at 1554050687851 with null payload
```

## How to start
### Before start
Add maven repository
```groovy 
buildscript {
    repositories {
        google()
        jcenter()

        maven {
            url  "https://dl.bintray.com/ivanksk/pulkovo"
        }
    }
}
```

### Basic usage

1. Add dependencies
```gradle
  debugImplementation 'com.ivanksk.pulkovo:debug:1.0'
  releaseImplementation 'com.ivanksk.pulkovo:release:1.0'
  implementation 'com.ivanksk.pulkovo:core:1.0'
```
2. Configure output with PulkovoDispatcher, you are all set with basic flow
```kotlin
class App : Application() {

    companion object {

        val WHAT_EVER_YOU_WANT_TAG = "TAG"

    }

    override fun onCreate() {
        super.onCreate()

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
    }
}
```

### Stats collector
Option when you want collect measurements into DB, analyze median, average, min and max values.
In case of need - share result CSV via all available methods at Android device.
1. Add dependencies:
```gradle
  debugImplementation 'com.ivanksk.pulkovo:debug:1.0'
  releaseImplementation 'com.ivanksk.pulkovo:production:1.0'
  implementation 'com.ivanksk.pulkovo:core:1.0'
  implementation 'com.ivanksk.pulkovo:statscollector:1.0'
```
2. Configure output with PulkovoDispatcher, you are all set with basic flow
```kotlin
class App : Application() {

    companion object {

        val WHAT_EVER_YOU_WANT_TAG = "TAG"

    }

    lateinit var statsReducer: StatsReducer

    override fun onCreate() {
        super.onCreate()
        statsReducer = StatsReducer(this)

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
            .addReducer(statsReducer)
    }
}
```
3. Create instance of ```StatsCollector``` in Application class, or in any DI module (should be singleton)


## Repository and dependencies 
To start using Pulkovo just add maven repo and all set of necessary 
```gradle
buildscript {
    repositories {
        google()
        jcenter()

        maven {
            url  "https://dl.bintray.com/ivanksk/pulkovo"
        }
    }
}
```
Add dependencies
```gradle
  debugImplementation 'com.ivanksk.pulkovo:debug:1.0' // for debug builds
  releaseImplementation 'com.ivanksk.pulkovo:release:1.0' // for release builds
  implementation 'com.ivanksk.pulkovo:core:1.0' 
  implementation 'com.ivanksk.pulkovo:statscollector:1.0' // to collect measurements into db
  implementation 'com.ivanksk.pulkovo:rxextensions:1.0' // to make measurements with RxJava
  
```

## Examples

```kotlin

  // here we measure method elapsed time, 
  // here we use custom label, it will tracked as "onStart method"
  override fun onStart() = measureMethodWithLabel("onStart method") {
    super.onStart()
    //
  }
  
  // here we measure method elapsed time, 
  // label will be tracked as MainActivity::onCreate, 
  // class & method name will extracted automatically
  override fun onCreate(savedInstanceState: Bundle?) = measureMethod(this) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setupClickListeners()
  }
  
  // measure RxJava chain, it's better
  private fun loadWeather() {
    // here we measure how much time will take data-load for RxJava chain
    startMeasure(
          Metrics.LoadWeather,
        // start measurement, initial observable
          weatherAPIClient.getWeather("Tallinn", "476f28ed531b9477e89ddb6ab463dbd5")
        )
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        // stop measurement
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
  
  
```

Example of StatsCollector usage, all, medians, average

```
  ********************************************************************************
  All records
  Display weather took 2 ms at 1553952094878 
  Load weather took 142 ms at 1553952094737 
  Display weather took 1 ms at 1553952096002
  Load weather took 142 ms at 1553952095860 
  Display weather took 2 ms at 1553952096432
  Load weather took 135 ms at 1553952096297 
  Display weather took 1 ms at 1553952096583
  Load weather took 135 ms at 1553952096449 
  Display weather took 1 ms at 1553952096741
  Load weather took 141 ms at 1553952096601 
  Display weather took 2 ms at 1553952096905
  Load weather took 151 ms at 1553952096755 
  Display weather took 2 ms at 1553952097065
  Load weather took 140 ms at 1553952096925
  Display weather took 2 ms at 1553952097217
  Load weather took 135 ms at 1553952097082 
  ********************************************************************************
  All Medians
  Median(label=Display weather, median=2, max=2, min=1)
  Median(label=Load weather, median=140, max=151, min=135)
  ********************************************************************************
  All Averages
  Average(label=Display weather, average=1.625, max=2, min=1)
  Average(label=Load weather, average=140.125, max=151, min=135)
  ********************************************************************************
```

## FAQ: Why Pulkovo?
It's kind of sentimental.. :) Pulkovo is nice a place close to Kotlin Island, just 30 mins drive to Kotlin Island, it's part of St-Petersburg, Russia. 
Pulkovo is very interesting place, here is located Airport, seesight viewpoint and *Pulkovo Astronomical Observatory* (what is part of the UNESCO World Heritage Site).
The *Pulkovo Meridian*, which passes through the center of the main building of the Observatory and is located at 30°19,6‘ east of Greenwich, was the point of departure for all former geographical maps of Russia. 
In former times *Pulkovo Meridian* was used as main meridian in Russian Empire, and what is interesting Pulkovo is located in the center of "Moscow timezone", so technically, time in MSK timezone is Pulkovo timezone..

[Pulkovo, is a start point where all measurements begins :)]

