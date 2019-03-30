package com.ivanksk.pulkovosample.network

import com.ivanksk.pulkovosample.model.CityWeather
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val baseUrl = "https://api.openweathermap.org"

val weatherAPIClient = Retrofit.Builder()
    .client(OkHttpClient())
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build().create(WeatherAPI::class.java)

interface WeatherAPI {
    @GET("/data/2.5/weather")
    fun getWeather(@Query("q") cityName: String, @Query("APPID") appID: String): Observable<CityWeather>
}