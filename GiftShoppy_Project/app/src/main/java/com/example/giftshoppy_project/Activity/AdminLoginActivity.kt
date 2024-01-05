package com.example.giftshoppy_project.Activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.giftshoppy_project.R
import com.example.giftshoppy_project.databinding.ActivityDashboardBinding

class AdminLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferencesUser: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferencesUser = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)


    }
}