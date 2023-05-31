package com.example.weatherapp.data.repo

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.entity.WeatherModel
import com.example.weatherapp.retrofit.ApiUtils
import com.example.weatherapp.retrofit.WeatherDao
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class WeatherDaoRepository {
    var weatherDao: WeatherDao
    private val disposable = CompositeDisposable()

    var weatherData = MutableLiveData<WeatherModel>()
    var weatherError = MutableLiveData<Boolean>()
    var weatherLoading = MutableLiveData<Boolean>()

    init {
        weatherDao = ApiUtils.getWeatherDao()
        weatherData = MutableLiveData()
        weatherError = MutableLiveData()
        weatherLoading = MutableLiveData()

    }


    fun getDataServices(name:String): Single<WeatherModel> {
        return weatherDao.getData(name)
    }


    fun getDataFromAPI(name: String){
        weatherLoading.value = true
        disposable.add(
            getDataServices(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {

                    override fun onSuccess(t: WeatherModel) {
                        weatherData.value = t
                        weatherError.value = false
                        weatherLoading.value = false
                        Log.d("ContentValues.TAG", "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        weatherError.value = true
                        weatherLoading.value = false
                        Log.e(ContentValues.TAG, "onError: " + e)
                    }

                })
        )
    }
}
