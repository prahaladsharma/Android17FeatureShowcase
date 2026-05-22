package com.example.android17featureshowcase.feature.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        Log.d("Worker", "Running background task")

        Thread.sleep(2000)

        return Result.success()
    }
}