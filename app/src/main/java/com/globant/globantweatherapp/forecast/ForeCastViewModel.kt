package com.globant.globantweatherapp.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.globantweatherapp.data.ForeCastWeather
import com.globant.globantweatherapp.location.GetLocationUseCase
import com.globant.globantweatherapp.remote.WeatherApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback

@HiltViewModel
class ForeCastViewModel @Inject constructor(
    private val service: WeatherApi,
    private val getCurrentLocation: GetLocationUseCase
) : ViewModel() {

    private var _isRefreshing = MutableStateFlow(false)
    var isRefreshing = _isRefreshing.asStateFlow()

    private var _dailyForeCast =
        MutableStateFlow<ForeCastWeatherResult>(ForeCastWeatherResult.Loading)

    var dailyForeCast = _dailyForeCast.asStateFlow()

    fun getHourlyForeCastInfo() = viewModelScope.launch(Dispatchers.IO) {
        val currentLocation = getCurrentLocation.getLocation()

        currentLocation?.let { location ->
            service.getDailyForecastData(location.latitude, location.longitude)
                .enqueue(object : Callback, retrofit2.Callback<ForeCastWeather> {
                    override fun onResponse(
                        call: Call<ForeCastWeather>,
                        response: Response<ForeCastWeather>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                _dailyForeCast.value = ForeCastWeatherResult.Success(it)
                                _isRefreshing.value = false
                            }
                        }
                    }

                    override fun onFailure(call: Call<ForeCastWeather>, t: Throwable) {
                        _dailyForeCast.value = ForeCastWeatherResult.Error("")
                        _isRefreshing.value = false
                    }
                })
        }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            getHourlyForeCastInfo()
            _isRefreshing.emit(true)
        }
    }
}