package com.globant.globantweatherapp.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.globant.globantweatherapp.R

//Any new bottom navigation item to be added here
sealed class BottomNavItem(val route: String, val icon: ImageVector,@StringRes val label: Int) {
    object Home : BottomNavItem("Home", Icons.Default.Home, R.string.home )
    object Forecast : BottomNavItem("Forecast", Icons.Default.DateRange, R.string.forecast)
}
