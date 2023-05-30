package com.example.weatherapp.retrofit

import retrofit2.Retrofit
import retrofit2.create

class ApiUtils {
    companion object {

        //http://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=4d73a2820db31bb2a4ad082b29cae606

        val BASE_URL = "http://api.openweathermap.org/"

        fun getWeatherDao(): WeatherDao {
            return RetrofitClient.getClient(BASE_URL).create(WeatherDao::class.java)
        }
    }
}