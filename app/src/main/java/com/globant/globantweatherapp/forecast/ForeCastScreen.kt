package com.globant.globantweatherapp.forecast

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.globant.globantweatherapp.data.ForeCastWeather
import com.globant.globantweatherapp.data.ListItem
import com.globant.globantweatherapp.ui.theme.Greenn
import com.globant.globantweatherapp.utils.AppStrings
import com.globant.globantweatherapp.utils.Utils
import com.globant.globantweatherapp.utils.Utils.convertDegToFar
import com.globant.globantweatherapp.utils.Utils.convertToMPH

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ForeCastScreen(foreCastViewModel: ForeCastViewModel) {
    val dailyForeCast by foreCastViewModel.dailyForeCast.collectAsState()
    val isRefreshing by foreCastViewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        foreCastViewModel.refresh()
    })

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {
        Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
            DailyForeCast(dailyForeCast)
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
fun DailyForeCast(foreCastWeatherResult: ForeCastWeatherResult) {
    when (foreCastWeatherResult) {
        is ForeCastWeatherResult.Success -> {
            SetDailyForeCastData(foreCastWeatherResult.forecast)
        }

        is ForeCastWeatherResult.Error -> {
            ForeCastWeatherResult.Error("")
        }

        is ForeCastWeatherResult.Loading -> {

        }
    }
}

@Composable
fun SetDailyForeCastData(forecast: ForeCastWeather) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 75.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val list = forecast.list.takeLast(32)
        items(list.size) {
            DailyWeatherCard(list[it])
        }
    }
}

@Composable
fun DailyWeatherCard(listItem: ListItem) {
    val weather = listItem.weather.first()
    val imageIcon = weather.icon
    val description = weather.description
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = MaterialTheme.shapes.small,
        ) {
            Row(
                modifier = Modifier
                    .background(Greenn)
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(2.2f)) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Hi Temp : ${listItem.main.tempMax.toInt().convertDegToFar()}° F",
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier,
                    )
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Low Temp : ${listItem.main.tempMin.toInt().convertDegToFar()}° F",
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier,
                    )
                }

                Column(modifier = Modifier.weight(2.5f)) {
                    Text(
                        text = Utils.readTimestamp(listItem.dt),
                        style = MaterialTheme.typography.caption,
                    )
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Wind Speed: ${listItem.wind.speed.convertToMPH()} ${AppStrings.metric}",
                        style = MaterialTheme.typography.caption,
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1.25f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/${imageIcon}@2x.png"),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )

                    Text(
                        textAlign = TextAlign.Center,
                        text = description,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}
