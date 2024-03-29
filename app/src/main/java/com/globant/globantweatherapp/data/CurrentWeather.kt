package com.globant.globantweatherapp.data

import com.google.gson.annotations.SerializedName

data class CurrentWeather(

    @field:SerializedName("visibility") val visibility: Int,

    @field:SerializedName("timezone") val timezone: Int,

    @field:SerializedName("main") val main: Main,

    @field:SerializedName("clouds") val clouds: Clouds,

    @field:SerializedName("sys") val sys: Sys,

    @field:SerializedName("dt") val dt: Int,

    @field:SerializedName("coord") val coord: Coord,

    @field:SerializedName("weather") val weather: List<WeatherItem>,

    @field:SerializedName("name") val name: String,

    @field:SerializedName("cod") val cod: Int,

    @field:SerializedName("id") val id: Int,

    @field:SerializedName("base") val base: String,

    @field:SerializedName("wind") val wind: Wind
)

data class Sys(

    @field:SerializedName("country") val country: String,

    @field:SerializedName("sunrise") val sunrise: Int,

    @field:SerializedName("sunset") val sunset: Int,

    @field:SerializedName("id") val id: Int,

    @field:SerializedName("type") val type: Int,

    @field:SerializedName("pod") val pod: String
)

data class Coord(

    @field:SerializedName("lon") val lon: Double,

    @field:SerializedName("lat") val lat: Double
)

data class WeatherItem(

    @field:SerializedName("icon") val icon: String,

    @field:SerializedName("description") val description: String,

    @field:SerializedName("main") val main: String,

    @field:SerializedName("id") val id: Long
)

data class Clouds(

    @field:SerializedName("all") val all: Int
)
