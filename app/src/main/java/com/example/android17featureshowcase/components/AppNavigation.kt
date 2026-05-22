package com.example.android17featureshowcase.components

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android17featureshowcase.feature.FeatureListScreen
import com.example.android17featureshowcase.feature.adaptive.AdaptiveScreen
import com.example.android17featureshowcase.feature.biometric.BiometricScreen
import com.example.android17featureshowcase.feature.contact.ContactScreen
import com.example.android17featureshowcase.feature.worker.WorkerScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable("home"){
            FeatureListScreen(navController)
        }
        composable("contacts") {
            ContactScreen()
        }

        composable("biometric") {
            BiometricScreen()
        }

        composable("worker") {
            WorkerScreen()
        }

        composable("adaptive") {
            AdaptiveScreen()
        }
    }
}