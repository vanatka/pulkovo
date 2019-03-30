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
  
```

#StatsCollector
tbd

#RxExtensions
tbd

#Debug and Release builds
tbd

#Gradle dependency
tbd
