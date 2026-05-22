package com.example.android17featureshowcase.feature.worker

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

@Composable
fun WorkerScreen() {

    val context = LocalContext.current

    Button(onClick = {

        val request =
            OneTimeWorkRequestBuilder<SyncWorker>().build()

        WorkManager.getInstance(context).enqueue(request)

    }) {
        Text("Start Background Work")
    }
}