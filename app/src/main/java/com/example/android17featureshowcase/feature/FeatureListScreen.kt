package com.example.android17featureshowcase.feature

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.android17featureshowcase.components.FeatureCard

@Composable
fun FeatureListScreen(navController: NavHostController) {

    val features = listOf(
        "Contacts" to "contacts",
        "Biometric" to "biometric",
        "Worker" to "worker",
        "Adaptive" to "adaptive"
    )

    LazyColumn {
        items(features){ (title, route) ->
            FeatureCard(title){
                navController.navigate(route)
            }
        }
    }
}