package com.example.workmanager_kotlin_android.Worker

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class DownloadWorker (context: Context, params:WorkerParameters):Worker(context,params) {
    @SuppressLint("SimpleDateFormat")
    override fun doWork(): Result {
        return try {
            for (i in 0..30){
                Log.d("MainWork", "DownloadWorker: $i ")
            }
            //output Data
            val time = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
            val currentDate = time.format(Date())
            Log.d("MainWork", "DownloadWorker Completed PeriodicTime: $currentDate ")


            Result.success()

        }catch (e:Exception){
            Result.failure()
        }
    }
}