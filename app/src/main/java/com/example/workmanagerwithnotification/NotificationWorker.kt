package com.example.workmanagerwithnotification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(var context: Context,parameters: WorkerParameters):Worker(context, parameters) {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        getnofitication()
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getnofitication() {
        var notification:NotificationManager
        var notificationChannel:NotificationChannel
        var builder:NotificationCompat.Builder

        var data=inputData.getInt("data",0)

        var image=BitmapFactory.decodeResource(applicationContext.resources,R.drawable.naruto)

        var contentview=RemoteViews(context.packageName,R.layout.customnotification)
        contentview.setTextViewText(R.id.title,"custom")
        contentview.setTextViewText(R.id.des,"des")
//        contentview.setImageViewBitmap(R.id.iv,image)

            Log.d("data","$data")
        var intent= Intent(applicationContext,MainActivity::class.java).apply {
    //            flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        var imageLarge=BitmapFactory.decodeResource(applicationContext.resources,R.drawable.naruto)
        var pendingIntent= PendingIntent.getActivity(applicationContext,0,intent,0)

        notificationChannel= NotificationChannel("id","notification",NotificationManager.IMPORTANCE_DEFAULT)
        notification=applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notification.createNotificationChannel(notificationChannel)
        builder=NotificationCompat.Builder(context,"id")
//            .setLargeIcon(image)
                .setSmallIcon(R.drawable.naruto)
//            .setContentTitle("notification")
//            .setSubText("subtitle")
//            .setColor(ContextCompat.getColor(context, R.color.teal_700))
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(imageLarge))
//            .setStyle(NotificationCompat.BigTextStyle().bigText("texttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttexttext"))
//            .setContentText("hoooraayyyyyy you got it")
            .setCustomContentView(contentview)
            .setContentIntent(pendingIntent)

        notification.notify(1,builder.build())
    }
}