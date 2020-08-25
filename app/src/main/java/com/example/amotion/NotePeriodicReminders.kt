package com.example.amotion

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.work.*
import com.example.amotion.work.ReminderNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

object NotePeriodicReminders{

    private val applicationScope = CoroutineScope(Dispatchers.Default)

        fun delayedInit(){

        applicationScope.launch {
            setUpRecurringWork()
        }

    }

    private fun setUpRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
//            .apply {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    setRequiresDeviceIdle(true)
//                }
//            }
            .build()

        val myWorkRequest = PeriodicWorkRequestBuilder<ReminderNote>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            ReminderNote.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            myWorkRequest)
        Log.i("workManajer", "Work initiated")
    }
}