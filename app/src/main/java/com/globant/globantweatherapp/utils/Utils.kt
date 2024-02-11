package com.globant.globantweatherapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.round
import kotlin.math.roundToInt

object Utils {

    fun convertHour(hour: String): String {
        return if (hour.toInt() in 1..11) {
            "$hour AM"
        } else {
            when (hour) {
                "00" -> {
                    "12 AM"
                }

                "12" -> {
                    "12 PM"
                }

                else -> {
                    "0${hour.toInt() - 12} PM"
                }
            }
        }
    }

    fun Int.convertDegToFar(): Int {
        return ((this * 9) / 5) + 32;
    }

    fun Double.convertToMPH(): Int {
        return (this / 1.6).toInt()
    }

    @SuppressLint("SimpleDateFormat")
    fun readTimestamp(timestamp: Long): String {
        val formatter = SimpleDateFormat("EEEE, MMMM dd")
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp * 1000L
        return formatter.format(calendar.time)
    }
}