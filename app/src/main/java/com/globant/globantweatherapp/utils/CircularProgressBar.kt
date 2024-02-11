package com.globant.globantweatherapp.utils

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.globant.globantweatherapp.ui.theme.Greenn

@Composable
fun CircularProgressBar(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = modifier, color = Greenn, strokeWidth = 3.dp)
}