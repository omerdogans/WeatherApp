package com.example.weatherapp.retrofit

import com.example.weatherapp.data.entity.WeatherAnswer
import com.example.weatherapp.data.entity.WeatherModel
import io.reactivex.Single
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WeatherDao {
    @GET("data/2.5/weather?&units=metric&APPID=5c3d7540f6b896b24560484b8d70f9df")
    fun getData(
        @Query("q") name:String
    ): Single<WeatherModel>
}