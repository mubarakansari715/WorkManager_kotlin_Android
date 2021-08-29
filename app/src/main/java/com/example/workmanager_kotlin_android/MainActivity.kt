package com.example.workmanager_kotlin_android

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.work.*
import com.example.workmanager_kotlin_android.Worker.CompressingWorker
import com.example.workmanager_kotlin_android.Worker.DownloadWorker
import com.example.workmanager_kotlin_android.Worker.FilterWorker
import com.example.workmanager_kotlin_android.Worker.UploadWorker
import com.example.workmanager_kotlin_android.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    companion object {
        const val KEY_VALUE = "key_value"
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnOneTimeRequest.setOnClickListener {
            setOneTimeRequest()
        }
        binding.btnPeriodicRequest.setOnClickListener {
            setPeriodicRequest()
        }


    }


    private fun setOneTimeRequest() {
        val workManager = WorkManager.getInstance(this)

        val constraint = Constraints.Builder()
            .setRequiresCharging(true) //required plug charger
            .setRequiredNetworkType(NetworkType.CONNECTED) // required on internet
            .build()
        val data = Data.Builder()
            .putInt(KEY_VALUE, 10)
            .build()
        //start
        val uploadWorkerRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraint)
            .setInputData(data)
            .build()

        //filterclass
        val filterWorker = OneTimeWorkRequest.Builder(FilterWorker::class.java)
            .build()
        val compressingWorker = OneTimeWorkRequest.Builder(CompressingWorker::class.java)
            .build()
        //download paraller execution
        val downloadWorker = OneTimeWorkRequest.Builder(DownloadWorker::class.java)
            .build()
        val parallerWorkers = mutableListOf<OneTimeWorkRequest>()
        parallerWorkers.add(downloadWorker)
        parallerWorkers.add(filterWorker)

        workManager
            .beginWith(parallerWorkers)
            .then(compressingWorker)
            .then(uploadWorkerRequest)
            .enqueue()
        //end

        //liveData by Id
        workManager.getWorkInfoByIdLiveData(uploadWorkerRequest.id)
            .observe(this, {
                binding.tvStatus.text = it.state.name
                //Output data Date and time toast
                if (it.state.isFinished) {
                    val date = it.outputData
                    val message = date.getString(UploadWorker.KEY_WORK)
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun setPeriodicRequest() {
        val workManager = WorkManager.getInstance(this)
        val periodicWorkRequest =
            PeriodicWorkRequestBuilder<DownloadWorker>(2, TimeUnit.MINUTES).build()
        workManager.enqueue(periodicWorkRequest)
    }


}