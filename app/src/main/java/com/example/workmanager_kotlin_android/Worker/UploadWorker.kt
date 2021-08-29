package com.example.workmanager_kotlin_android.Worker

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanager_kotlin_android.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class UploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    companion object {
        const val KEY_WORK = "key_work"
    }

    @SuppressLint("SimpleDateFormat")
    override fun doWork(): Result {
        try {
            //Input Data
            val count = inputData.getInt(MainActivity.KEY_VALUE, 0)
            //Data
            for (i in 0 until count) {
                Log.d("MainWork", "doWork: UploadWorker $i")
            }
            //output Data
            val time = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
            val currentDate = time.format(Date())
            val outPutDate = Data.Builder()
                .putString(KEY_WORK, currentDate)
                .build()

            return Result.success(outPutDate)

        } catch (e: Exception) {
            Log.d("MainWork", "doWorkException: $e")
            return Result.failure()
        }
    }
}