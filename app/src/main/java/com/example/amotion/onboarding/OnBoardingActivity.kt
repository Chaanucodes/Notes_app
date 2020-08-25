package com.example.amotion.onboarding

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.amotion.MainActivity
import com.example.amotion.R
import com.example.amotion.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)
        window.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE
        }

        Toast.makeText(this, "Swipe after reading >>>", Toast.LENGTH_LONG).show()

        val adapter = OnBoardAdapter()
        adapter.items =
            listOf(
                OnBoardingItems(R.drawable.ic_adding_bg, "New blue theme, easy on the eyes with a more customized button to add a new note."),
                OnBoardingItems(R.drawable.ic_baseline_share_24, "Now share your notes in Whatsapp and other platform using the share button."),
                OnBoardingItems(R.drawable.ic_remind, "Note taking is highly underrated, we make sure you are gently reminded once a day" +
                        " to write a new note or update an old one through a notification.")
            )

        binding.onBoardRecyclerView.adapter = adapter
        binding.onBoardRecyclerView.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.prevButton.visibility = View.INVISIBLE
                        ObjectAnimator.ofInt(binding.progressHorizontal, "progress", 33)
                            .setDuration(300)
                            .start()
                        binding.toAppButton.text = "Next"
                    }
                    1 -> {
                        binding.prevButton.visibility = View.VISIBLE
                        ObjectAnimator.ofInt(binding.progressHorizontal, "progress", 66)
                            .setDuration(300)
                            .start()
                        binding.toAppButton.text = "Next"
                    }
                    2 -> {
                        ObjectAnimator.ofInt(binding.progressHorizontal, "progress", 99)
                            .setDuration(300)
                            .start()
                        binding.toAppButton.text = "To App"
                    }
                }
                super.onPageSelected(position)
            }
        })

        binding.prevButton.setOnClickListener {
            binding.onBoardRecyclerView.currentItem -= 1
        }

        binding.toAppButton.setOnClickListener {
            if(binding.onBoardRecyclerView.currentItem!=2){
                binding.onBoardRecyclerView.currentItem += 1
            }else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}

data class OnBoardingItems(
    val img : Int,
    val desc : String
)