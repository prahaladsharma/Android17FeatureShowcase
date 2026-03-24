package com.example.android17featureshowcase.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FeatureCard(
    title: String,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable{ onClick() },
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(20.dp),
            fontSize = 18.sp
        )
    }
}