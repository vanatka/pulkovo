package com.ivanksk.pulkovosample.model

class CityWeather {

    var city: City? = null

    var weather: ArrayList<Weather>? = null

    var main: Temp? = null
}

class City {

    var id: Int = 0

    var name: String? = null

    var country: String? = null

}

class Temp {

    var temp: Double = 0.0

    var humidity: Double = 0.0

}

class Weather {

    var main: String? = null

    var description: String? = null


}