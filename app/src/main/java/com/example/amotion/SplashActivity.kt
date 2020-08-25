package com.example.amotion

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.amotion.databinding.ActivitySplashBinding
import com.example.amotion.onboarding.OnBoardingActivity
import com.example.amotion.work.CHANNEL_ID_OLD

const val sharedPrefFileName = "com.example.amotion"
const val keyH = "Ke00AMO_INT"

class SplashActivity : AppCompatActivity() {

//    lateinit var anim : AnimationDrawable
    private lateinit var sharedPreferences : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySplashBinding>(this ,R.layout.activity_splash )

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        window.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE
        }


        sharedPreferences =
            getSharedPreferences(sharedPrefFileName, Context.MODE_PRIVATE)
        val num = sharedPreferences.getInt(keyH, 3)


        Handler().postDelayed({
            if(num==2) goToMainActivity()
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    deleteNotiChannel()
                }
                goToOnBoardingActivity()
            }
        }, 500)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun deleteNotiChannel() {
        val notificationManager: NotificationManager =
            ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.deleteNotificationChannel(CHANNEL_ID_OLD)
    }

    private fun goToOnBoardingActivity() {
        val intent = Intent(this, OnBoardingActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val preferenceEditor : SharedPreferences.Editor = sharedPreferences.edit()
        preferenceEditor.putInt(keyH, 2)
        preferenceEditor.apply()
        startActivity(intent)
        finish()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val preferenceEditor : SharedPreferences.Editor = sharedPreferences.edit()
        preferenceEditor.putInt(keyH, 2)
        preferenceEditor.apply()
        startActivity(intent)
        finish()
    }
}
