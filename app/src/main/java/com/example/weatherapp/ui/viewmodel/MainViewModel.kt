package com.example.weatherapp.ui.viewmodel

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.entity.WeatherModel
import com.example.weatherapp.data.repo.WeatherDaoRepository
import com.example.weatherapp.retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel:ViewModel() {

    val weatherRepo = WeatherDaoRepository()

    fun refreshData(cityName: String){
       weatherRepo.getDataFromAPI(cityName)
    }

}