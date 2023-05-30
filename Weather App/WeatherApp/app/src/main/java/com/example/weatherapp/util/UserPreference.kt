package com.example.weatherapp.util

import android.content.Context
import android.content.SharedPreferences

class UserPreference(private val context: Context) {

    private val PREFS_FILE_NAME = "WeatherAppPrefs"
    private var sharedPreferences: SharedPreferences =  context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)

    open fun getLastCityNameEntered():String?{


        val lastCityName = sharedPreferences.getString("last_city", "")

        return lastCityName
    }

    open fun saveCity(cityName:String){
        sharedPreferences.edit().putString("last_city", cityName).apply()
    }



}