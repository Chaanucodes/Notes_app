package com.example.amotion.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.amotion.MainActivity
import com.example.amotion.R
import java.lang.Exception
import kotlin.random.Random

const val CHANNEL_ID_OLD = "com_ID_A_MOTION"
const val CHANNEL_ID = "com_ID_A_NEW_MOTION"
const val notificationId = 1
const val REQ_CODE = 2

class ReminderNote(appContext : Context, params : WorkerParameters) : CoroutineWorker(appContext, params){

    companion object {
        const val WORK_NAME = "com.example.amotion.work.ReminderNote_WORK"
    }

    override suspend fun doWork(): Result {

        try {
//            val random = Random(4).nextInt()
            createNoti()
        }catch (e : Exception){
            return Result.retry()
        }

        return Result.success()
    }

    private fun createNoti() {
        val i = Random.nextInt(0,4)
        val reminders = listOf(
            "What you write today will be the base of what you speak tomorrow",
            "How you play with words define the role you play in your world",
            "An idea you discard today can transform itself to a regret tomorrow. Don't let your ideas go away!",
            "The devil is in the details. Right? Let's right some of'em details",
            "What you are able to recall is easy and the opposite is difficult, write something so it gets easier to recall tomorrow")



        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            REQ_CODE,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val largeImg = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.idea
        )

        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_remind)
            .setLargeIcon(largeImg)
            .setContentTitle("Write something")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentText(reminders[i])
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(
                    reminders[i]))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .also {
                it.color =  applicationContext.resources.getColor(R.color.colorPrimary)
                it.setColorized(true)
            }

        with(NotificationManagerCompat.from(applicationContext)){
            notify(notificationId, builder.build())
        }

    }





}