package com.example.workmanager_kotlin_android.Worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class CompressingWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        return try {
            for (i in 0..5) {
                Log.d("MainWork", "CompressingWorker: $i ")
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }

    }
}