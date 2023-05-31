package com.example.weatherapp.ui.activity

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.data.entity.Weather
import com.example.weatherapp.data.entity.WeatherModel
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.ui.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        var cName = GET.getString("cityName", "bingöl")?.toLowerCase()
        binding.etCityName.setText(cName)
        viewModel.refreshData(cName!!)

        getDataLive()



        binding.ivSearchEnter.setOnClickListener {
            val cityName = binding.etCityName.text.toString()
            SET.putString("cityName", cityName)
            SET.apply()
            viewModel.refreshData(cityName)
            getDataLive()
            Log.d("Tıklandııı", "Tıklandııııı : Şehirrr")
            Log.d("Tıklandııı", "Tıklandııııı : Şehirrr : " + binding.etCityName.toString())

        }

    }


    @SuppressLint("SuspiciousIndentation")
    private fun getDataLive() {
        viewModel.weatherRepo.weatherData.observe(this, Observer { data ->
            data?.let {
                binding.tvCityName.text = data.name.toString()

                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.weather.get(0).icon + "@4x.png")
                    .into(binding.ivWeatherIcon)

                binding.tvDegree.text = data.main.temp.toString() + "°C"
                binding.tvWindSpeed.text = data.wind.speed.toString() + " km/h"
                binding.tvHumidity.text = data.main.humidity.toString() + "%"
                binding.tvLatitude.text = data.coord.lat.toString()
                binding.tvLongitude.text = data.coord.lon.toString()
                binding.tvDescription.text ="It's " + data.weather[0].description


                Log.d("derece","derece ${data.main.temp}" )
            }
        })

        viewModel.weatherRepo.weatherError.observe(this, Observer { error ->
            error?.let {

                    if (error) {
                        binding.tvError.visibility = View.VISIBLE
                        binding.pbLoading.visibility = View.GONE
                    } else {
                        binding.tvError.visibility = View.GONE
                    }


            }
        })

        viewModel.weatherRepo.weatherLoading.observe(this, Observer { loading ->
            loading?.let {

                    if (loading) {
                        binding.pbLoading.visibility = View.VISIBLE
                        binding.tvError.visibility = View.GONE
                    } else {
                        binding.pbLoading.visibility = View.GONE
                    }


            }
        })
    }


}