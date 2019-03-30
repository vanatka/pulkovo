# Pulkovo. Kotlin library to measure method elapsed time.

Kotlin library to measure elapsed time for Kotlin code: methods, code blocks, or with just start and stop methods call

*Brief description:*
Sometimes it's necessary measure, how much time it takes to execute some block of code.
In java-android-world there are such solutions like Pury, Meter and etc.
However they are not Kotlin friendly. 

This library has few concepts:
- simplicity: 
- flexible extesibility: synchronous API to make it scalable without huge dependecies
- plugins-friendly
- no code change for prod and debug version

## Structure
tbd

## How to start
1. Add dependency 
2. Configure out
```
```

## Dependency 
To start using Pulkovo just add maven repo and all set of necessary 
```
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
```
  debugImplementation 'com.ivanksk.pulkovo:debug:1.0'
  releaseImplementation 'com.ivanksk.pulkovo:production:1.0'
  implementation 'com.ivanksk.pulkovo:core:1.0'
  implementation 'com.ivanksk.pulkovo:statscollector:1.0'
  implementation 'com.ivanksk.pulkovo:rxextensions:1.0'
  
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
  Display weather took 2 ms at 1553952094878 with  payload
  Load weather took 142 ms at 1553952094737 with  payload
  Display weather took 1 ms at 1553952096002 with  payload
  Load weather took 142 ms at 1553952095860 with  payload
  Display weather took 2 ms at 1553952096432 with  payload
  Load weather took 135 ms at 1553952096297 with  payload
  Display weather took 1 ms at 1553952096583 with  payload
  Load weather took 135 ms at 1553952096449 with  payload
  Display weather took 1 ms at 1553952096741 with  payload
  Load weather took 141 ms at 1553952096601 with  payload
  Display weather took 2 ms at 1553952096905 with  payload
  Load weather took 151 ms at 1553952096755 with  payload
  Display weather took 2 ms at 1553952097065 with  payload
  Load weather took 140 ms at 1553952096925 with  payload
  Display weather took 2 ms at 1553952097217 with  payload
  Load weather took 135 ms at 1553952097082 with  payload
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

# StatsCollector
tbd

# RxExtensions
tbd

# Debug and Release builds
There are 2 options for debug and production builds.
To do

```

```

# Gradle dependency
tbd
