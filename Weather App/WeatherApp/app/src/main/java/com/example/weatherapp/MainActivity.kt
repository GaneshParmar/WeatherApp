package com.example.weatherapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.util.UserPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.net.URL


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userPreference = UserPreference(applicationContext)

        var lastCityName = getLastCityEntered()

        SearchWeather(lastCityName)


        binding.apply {
            searchBtn.setOnClickListener {

                var enteredTxt = editText.text.toString()

                if (enteredTxt == "") {
                    return@setOnClickListener
                }

                userPreference.saveCity(enteredTxt)
                SearchWeather(enteredTxt)


            }

        }


    }

    private fun getLastCityEntered(): String {
        var city = userPreference.getLastCityNameEntered()
        if (city == "" || city.isNullOrEmpty()) {
            return "Mumbai"
        }
        return city
    }

    fun SearchWeather(cityNaame: String) {

        binding.progress.visibility = View.VISIBLE
//        binding.txtView.visibility = View.GONE


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response =
                    WeatherRetrofitInstance.weatherService.getWeather(cityNaame, "json", "c")
                // Handle the response
                // ...

                Log.d("Tagy",response.toString())

                response.imgUrl = "https://s.yimg.com/zz/combo?a/i/us/nws/weather/gr/${response.current_observation.condition.code}d.png"
                withContext(Dispatchers.Main) {
                    Glide.with(this@MainActivity)
                         .load(response.imgUrl) // Optional placeholder image while loading
                        .placeholder(R.drawable.placeholder)
                         .into(binding.imgView)
                        // Handle the response
                        binding.weather = response


//                       binding.txtView.text = response.toString()
                    binding.progress.visibility = View.GONE
//                    binding.txtView.visibility = View.VISIBLE
                }

            } catch (e: Exception) {
                // Handle the error
                // ...
                withContext(Dispatchers.Main) {

//                    binding.txtView.text = e.toString() +" Please check the internet Connection or city name!"
                    binding.progress.visibility = View.GONE
                    Toast.makeText(this@MainActivity,e.message.toString(),Toast.LENGTH_LONG).show()
//                    binding.txtView.visibility = View.VISIBLE}
                }
            }
        }


    }

}

object WeatherRetrofitInstance {
    private const val BASE_URL = "https://yahoo-weather5.p.rapidapi.com/"
//    'X-RapidAPI-Key': '06f5eaf66cmsh8e4034c6e02446ep12dfeejsn9b9a06329ae0',
//    'X-RapidAPI-Host': 'yahoo-weather5.p.rapidapi.com'
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val request = originalRequest.newBuilder()
                .header("X-RapidAPI-Key", "06f5eaf66cmsh8e4034c6e02446ep12dfeejsn9b9a06329ae0")
                .header("X-RapidAPI-Host", "yahoo-weather5.p.rapidapi.com")
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    val weatherService: WeatherApiService = retrofit.create(WeatherApiService::class.java)
}
