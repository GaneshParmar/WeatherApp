package com.example.weatherapp

import android.graphics.drawable.Drawable

data class WeatherResponse(
    val location: Location,
    val current_observation: CurrentObservation,
    val forecasts: List<Forecast>,
    var imgUrl: String
)

data class Location(
    val city: String,
    val woeid: Int,
    val country: String,
    val lat: Double,
    val long: Double,
    val timezone_id: String
)

data class CurrentObservation(
    val pubDate: Long,
    val wind: Wind,
    val atmosphere: Atmosphere,
    val astronomy: Astronomy,
    val condition: Condition
)

data class Wind(
    val chill: Int,
    val direction: String,
    val speed: Int
)

data class Atmosphere(
    val humidity: Int,
    val visibility: Int,
    val pressure: Double
)

data class Astronomy(
    val sunrise: String,
    val sunset: String
)

data class Condition(
    val temperature: Int,
    val text: String,
    val code: Int
)

data class Forecast(
    val day: String,
    val date: Long,
    val high: Int,
    val low: Int,
    val text: String,
    val code: Int
)