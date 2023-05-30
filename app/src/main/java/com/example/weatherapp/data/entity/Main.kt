package com.example.weatherapp.data.entity


import com.google.gson.annotations.SerializedName

data class Main(
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Int,
    val tempMax: Double,
    val tempMin: Double
)