package com.example.weatherapp.data.repo

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.entity.WeatherAnswer
import com.example.weatherapp.data.entity.WeatherModel
import com.example.weatherapp.retrofit.ApiUtils
import com.example.weatherapp.retrofit.RetrofitClient
import com.example.weatherapp.retrofit.WeatherDao
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherDaoRepository {
    var weatherDao: WeatherDao
    private val disposable = CompositeDisposable()

    val weatherData = MutableLiveData<WeatherModel>()
    val weatherError = MutableLiveData<Boolean>()
    val weatherLoading = MutableLiveData<Boolean>()

    init {
        weatherDao = ApiUtils.getWeatherDao()
    }

    //APiServicesin içindeki
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
