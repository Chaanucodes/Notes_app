package com.example.amotion


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.amotion.databinding.ActivityMainBinding
import com.example.amotion.work.CHANNEL_ID


class MainActivity : AppCompatActivity() {


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        window.navigationBarColor = resources.getColor(R.color.black)


        window.statusBarColor = resources.getColor(R.color.black)
        createNotiChannel()
        NotePeriodicReminders.delayedInit()


    }

    override fun onBackPressed() {
        val navController = this.findNavController(R.id.myNavHostFragment)
        if (navController.currentDestination?.id == R.id.editNoteFragment) {
            navController.navigate(R.id.action_editNoteFragment_to_titleFragment)
        } else {
            super.onBackPressed()
        }
    }

    private fun createNotiChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = applicationContext.resources.getString(R.string.channel_name)
            val descriptionText =
                applicationContext.resources.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                ContextCompat.getSystemService(
                    applicationContext,
                    NotificationManager::class.java
                ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
