package com.globant.globantweatherapp.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.globant.globantweatherapp.data.CurrentWeather
import com.globant.globantweatherapp.data.ForeCastWeather
import com.globant.globantweatherapp.data.ListItem
import com.globant.globantweatherapp.forecast.ForeCastWeatherResult
import com.globant.globantweatherapp.ui.theme.Gray
import com.globant.globantweatherapp.ui.theme.Greenn
import com.globant.globantweatherapp.utils.AppStrings
import com.globant.globantweatherapp.utils.CircularProgressBar
import com.globant.globantweatherapp.utils.Utils
import com.globant.globantweatherapp.utils.Utils.convertDegToFar
import com.globant.globantweatherapp.utils.Utils.convertToMPH

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val isRefreshing by homeViewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        homeViewModel.refresh()
    })

    val homeCurrentWeatherState by homeViewModel.currentWeatherInfo.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
            Column(modifier = Modifier.background(Gray)) {
                CurrentWeather(homeCurrentWeatherState, homeViewModel)
            }
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 30.dp)
            )
        }
    }
}

@Composable
fun CurrentWeather(homeWeatherResult: HomeWeatherResult, homeViewModel: HomeViewModel) {
    when (homeWeatherResult) {
        is HomeWeatherResult.Success -> {
            CurrentWeatherSection(homeWeatherResult.currentWeather)
        }

        is HomeWeatherResult.Error -> {

        }

        is HomeWeatherResult.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressBar(modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp / 9))
            }
        }
    }
    GetHourlyForeCast(homeViewModel)
}


@Composable
fun GetHourlyForeCast(homeViewModel: HomeViewModel) {
    homeViewModel.cityName?.let {
        homeViewModel.getThreeHoursForecast(it)
    }
    val forecastCityData by homeViewModel.hourlyForeCastData.collectAsState()
    ForeCastCityData(forecastCityData)
}

@Composable
fun ForeCastCityData(forecastCityData: ForeCastWeatherResult) {
    when (forecastCityData) {
        is ForeCastWeatherResult.Success -> {
            SetHourlyForeCastData(forecastCityData.forecast)
        }

        is ForeCastWeatherResult.Error -> {}
        is ForeCastWeatherResult.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressBar(modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp / 9))
            }
        }
    }
}


@Composable
fun SetHourlyForeCastData(forecast: ForeCastWeather) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 75.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val list = forecast.list.take(8)
        items(list.size) {
            WeatherCard(list[it])
        }
    }
}

@Composable
fun WeatherCard(listItem: ListItem) {
    val imageIcon = listItem.weather.first().icon
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = MaterialTheme.shapes.small,
        ) {
            Row(modifier = Modifier.background(Greenn)) {
                Text(
                    text = Utils.convertHour(listItem.dtTxt.substring(11, 13)),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .weight(2f)
                        .padding(start = 10.dp, top = 10.dp)
                )
                Text(
                    textAlign = TextAlign.End,
                    text = "${listItem.main.temp.toInt().convertDegToFar()}° F",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .weight(2f)
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp),
                )
                Image(
                    painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/${imageIcon}@2x.png"),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}


@Composable
fun CurrentWeatherSection(currentWeather: CurrentWeather) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = currentWeather.name, style = MaterialTheme.typography.h4
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 15.dp)
        ) {
            Text(
                text = "Wind: \n" + currentWeather.wind.speed.convertToMPH() + " " + AppStrings.metric,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.weight(2f)
            )
            Text(
                textAlign = TextAlign.End,
                text = "Feels like: \n" + currentWeather.main.feelsLike + AppStrings.degree,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.weight(2f)
            )
        }
    }
}
