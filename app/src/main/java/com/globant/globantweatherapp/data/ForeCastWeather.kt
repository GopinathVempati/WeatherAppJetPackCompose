package com.globant.globantweatherapp.data

import com.google.gson.annotations.SerializedName

data class ForeCastWeather(

    @field:SerializedName("city") val city: City,

    @field:SerializedName("cnt") val cnt: Int,

    @field:SerializedName("cod") val cod: String,

    @field:SerializedName("message") val message: Any,

    @field:SerializedName("list") val list: List<ListItem>
)

data class City(

    @field:SerializedName("country") val country: String,

    @field:SerializedName("coord") val coord: Coord,

    @field:SerializedName("sunrise") val sunrise: Long,

    @field:SerializedName("timezone") val timezone: Long,

    @field:SerializedName("sunset") val sunset: Long,

    @field:SerializedName("name") val name: String,

    @field:SerializedName("id") val id: Long,

    @field:SerializedName("population") val population: Long
)


data class Main(
    @field:SerializedName("temp") val temp: Double,
    @field:SerializedName("temp_min") val tempMin: Double,
    @field:SerializedName("temp_max") val tempMax: Double,
    @field:SerializedName("feels_like") val feelsLike: Double,
    @field:SerializedName("grnd_level") val grndLevel: Long,
    @field:SerializedName("temp_kf") val tempKf: Any,
    @field:SerializedName("humidity") val humidity: Long,
    @field:SerializedName("pressure") val pressure: Long,
    @field:SerializedName("sea_level") val seaLevel: Long
)

data class Rain(

    @field:SerializedName("3h") val jsonMember3h: Double
)

data class Wind(

    @field:SerializedName("deg") val deg: Int,

    @field:SerializedName("speed") val speed: Double,

    @field:SerializedName("gust") val gust: Double
)

data class ListItem(

    @field:SerializedName("dt") val dt: Long,

    @field:SerializedName("pop") val pop: Any,

    @field:SerializedName("visibility") val visibility: Int,

    @field:SerializedName("dt_txt") val dtTxt: String,

    @field:SerializedName("weather") val weather: List<WeatherItem>,

    @field:SerializedName("main") val main: Main,

    @field:SerializedName("clouds") val clouds: Clouds,

    @field:SerializedName("sys") val sys: Sys,

    @field:SerializedName("wind") val wind: Wind,

    @field:SerializedName("rain") val rain: Rain
)
