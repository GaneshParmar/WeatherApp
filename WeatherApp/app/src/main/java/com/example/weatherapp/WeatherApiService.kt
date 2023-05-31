package com.example.weatherapp

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApiService {
    @Headers(
        "X-RapidAPI-Key: 0cadf53f12mshe990fd066c884e7p118e2djsnca4175b11c7f",
        "X-RapidAPI-Host: yahoo-weather5.p.rapidapi.com"
    )
    @GET("weather")
    suspend fun getWeather(
        @Query("location") location: String,
        @Query("format") format: String,
        @Query("u") units: String
    ): WeatherResponse
}
