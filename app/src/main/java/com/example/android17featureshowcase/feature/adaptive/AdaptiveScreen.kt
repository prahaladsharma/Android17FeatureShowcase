package com.example.android17featureshowcase.feature.adaptive

import android.annotation.SuppressLint
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.sp

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun AdaptiveScreen() {

    val config = LocalConfiguration.current

    if (config.screenWidthDp > 600) {
        TabletUI()
    } else {
        PhoneUI()
    }
}

@Composable
fun TabletUI() {
    Text("Tablet Layout", fontSize = 24.sp)
}

@Composable
fun PhoneUI() {
    Text("Phone Layout", fontSize = 18.sp)
}