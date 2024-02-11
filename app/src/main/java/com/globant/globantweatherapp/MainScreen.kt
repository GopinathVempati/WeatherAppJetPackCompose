package com.globant.globantweatherapp

import androidx.compose.material.Icon
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.globant.globantweatherapp.forecast.ForeCastScreen
import com.globant.globantweatherapp.home.HomeScreen
import com.globant.globantweatherapp.navigation.BottomNavItem
import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.globant.globantweatherapp.forecast.ForeCastViewModel
import com.globant.globantweatherapp.home.HomeViewModel
import com.globant.globantweatherapp.ui.theme.Greenn

/*This screen create bottom navigation and set navigation when tapped on bottom nav menu item*/
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    foreCastViewModel: ForeCastViewModel
) {
    Scaffold(bottomBar = {
        BottomAppBar(containerColor = Greenn) { BottomNavigationBar(navController = navController) }
    }) {
        NavigationScreens(
            navController = navController,
            homeViewModel = homeViewModel,
            foreCastViewModel = foreCastViewModel
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navItems = listOf(BottomNavItem.Home, BottomNavItem.Forecast)
    var selectedItem by rememberSaveable { mutableStateOf(0) }

    NavigationBar(containerColor = Greenn) {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                alwaysShowLabel = true,
                icon = { Icon(item.icon, contentDescription = "") },
                label = { Text(stringResource(item.label)) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationScreens(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    foreCastViewModel: ForeCastViewModel
) {
    NavHost(navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) { HomeScreen(homeViewModel) }
        composable(BottomNavItem.Forecast.route) { ForeCastScreen(foreCastViewModel) }
    }
}

