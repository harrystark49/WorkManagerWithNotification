package com.example.workmanagerwithnotification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notification.setOnClickListener {
            startworker()
        }
        periodicnotification.setOnClickListener {
            startPeriodicWorker()
        }
    }

    private fun startPeriodicWorker() {
        var worker=WorkManager.getInstance(this)
        var dataa:Data=Data.Builder().putInt("data",5).build()
        var periodicreq= PeriodicWorkRequest.Builder(NotificationWorker::class.java,16,TimeUnit.MINUTES)
            .setInputData(dataa)
            .build()

        worker.enqueue(periodicreq)

    }

    private fun startworker() {
        var worker=WorkManager.getInstance(this)
        var constraints=Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()
        var oneTimeWorkRequest=OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .setConstraints(constraints)
            .build()
            worker.enqueue(oneTimeWorkRequest)

        worker.getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this, Observer {
            Log.d("getstate","${it.state}")
        })
    }
}