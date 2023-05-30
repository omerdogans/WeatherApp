package com.example.weatherapp.ui.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.data.entity.WeatherModel
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.ui.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var city_name: String
    lateinit var degree: String
    lateinit var humidity: String
    lateinit var windSpeed:String

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        var cName = GET.getString("cityName", "bingöl")?.toLowerCase()
        Log.d("Main Şehir","Şehirr" + cName.toString())
        viewModel.refreshData(cName!!)

        getDataLive()
    }
    private fun getDataLive(){
        viewModel.weatherRepo.weatherData.observe(this, Observer { data ->
            data?.let {
            binding.tvCityName.text = data.name.toString()
           // degree = data.main.temp.toString()
          //  humidity = data.wind.speed.toString() + "%"

            Log.d("Main City Name "," city name : " + data.name.toString())
                Log.d("Main City Name temp"," city name : " + data.main.temp.toString())
                Log.d("Main City Name wind"," city name : " + data.wind.speed.toString())

            } })

        viewModel.weatherRepo.weatherError.observe(this, Observer { error -> error?.let {

        } })

        viewModel.weatherRepo.weatherLoading.observe(this, Observer { loading -> loading?.let {

        } })
    }


}