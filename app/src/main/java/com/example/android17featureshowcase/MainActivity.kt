package com.example.android17featureshowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.android17featureshowcase.components.AppNavigation
import com.example.android17featureshowcase.feature.FeatureListScreen
import com.example.android17featureshowcase.ui.theme.Android17FeatureShowcaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android17FeatureShowcaseTheme {
                //FeatureListScreen(navController)
                AppNavigation()
            }
        }
    }
}

