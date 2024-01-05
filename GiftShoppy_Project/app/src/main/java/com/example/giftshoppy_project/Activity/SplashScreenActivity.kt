
package com.example.giftshoppy_project.Activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.giftshoppy_project.R
import com.example.giftshoppy_project.databinding.ActivityCategoryProductBinding
import com.example.giftshoppy_project.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val DarkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
        val isDarkModeOn = DarkModeFlags == Configuration.UI_MODE_NIGHT_YES//Check if the Dark Mode is On

        if(isDarkModeOn)
        {
            binding.linearSplash.setBackgroundColor(Color.parseColor("#FBEAEB"))

        }
        else
        {
            binding.linearSplash.setBackgroundColor(Color.parseColor("#FBEAEB"))

        }
        Handler().postDelayed(Runnable {

            startActivity(Intent(applicationContext, WelcomeActivity::class.java))


        },3000)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}