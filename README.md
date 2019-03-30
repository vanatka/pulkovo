# pulkovo

Kotlin library to measure elapsed time for Kotlin code

Brief description:

```kotlin

  //custom label
  override fun onStart() = measureMethod("onStart method") {
    super.onStart()
    //
  }
  
  // label will be tracked as MainActivity::onCreate
  override fun onCreate(savedInstanceState: Bundle?) = measureMethod(this) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setupClickListeners()
  }
  
  // measure RxJava chain
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

#StatsCollector
tbd

#RxExtensions
tbd

#Debug and Release builds
tbd

#Gradle dependency
tbd
