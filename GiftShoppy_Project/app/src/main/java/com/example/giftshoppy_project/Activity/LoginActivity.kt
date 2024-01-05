package com.example.giftshoppy_project.Activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.giftshopproject.Client.ApiClient
import com.example.giftshopproject.Interface.Apiinterface
import com.example.giftshopproject.Model.SignupModel
import com.example.giftshoppy_project.R
import com.example.giftshoppy_project.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), MediaPlayer.OnPreparedListener {

    private lateinit var binding: ActivityLoginBinding

    lateinit var sharedPreferences: SharedPreferences
    lateinit var apiinterface: Apiinterface


    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val notConnected = intent.getBooleanExtra(
                ConnectivityManager
                    .EXTRA_NO_CONNECTIVITY, false)
            if (notConnected) {
                disconnected()
            } else {
                connected()
            }
        }
    }

    private fun disconnected()
    {
        //View.INVISIBLE
        //View.VISIBLE
        //imageView.visibility = View.VISIBLE
        binding.videoView.visibility = View.INVISIBLE
        binding.linearLogin.visibility = View.INVISIBLE
        binding.noInternet.visibility = View.VISIBLE



        Toast.makeText(applicationContext,"Not Connected",Toast.LENGTH_LONG).show()
    }

    private fun connected() {
        Toast.makeText(applicationContext,"Connected",Toast.LENGTH_LONG).show()
        binding.videoView.visibility = View.VISIBLE
        binding.linearLogin.visibility = View.VISIBLE
        binding.noInternet.visibility = View.INVISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val DarkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
        val isDarkModeOn = DarkModeFlags == Configuration.UI_MODE_NIGHT_YES//Check if the Dark Mode is On

        if(isDarkModeOn)
        {
            binding.noInternet.setBackgroundColor(Color.parseColor("#0d050e"))
            binding.edtPhone.setTextColor(Color.parseColor("#00203F"))

        }
        else
        {
            binding.noInternet.setBackgroundColor(Color.parseColor("#e4e5f1"))

        }

        val prefManager = PrefManagerClass(applicationContext)
        if (prefManager.isFirstTimeLaunch) {
            prefManager.isFirstTimeLaunch = false
            startActivity(Intent(applicationContext, WelcomeActivity::class.java))
            finish()
        }

        sharedPreferences = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("USER_SESSION", false) && !sharedPreferences.getString("phone", "")!!.isEmpty()) {
            startActivity(Intent(applicationContext, DashboardActivity::class.java))
            finish()
        }

        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.vide)
        binding.videoView.setVideoURI(uri)
        binding.videoView.start()


        binding.videoView.setOnPreparedListener(this)

        binding.textView3.setOnClickListener {
            startActivity(Intent(applicationContext,SignUpActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            var phone = binding.edtPhone.text.toString()
            var pass = binding.edtPassword.text.toString()
            apiinterface = ApiClient.getapiclient().create(Apiinterface::class.java)

            val call: Call<SignupModel> = apiinterface.loginuser(phone,pass)


            call.enqueue(object: Callback<SignupModel>
            {

                override fun onResponse(call: Call<SignupModel>, response: Response<SignupModel>) {

                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                    var i = Intent(applicationContext,DashboardActivity::class.java)
                    startActivity(i)
                    var editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putBoolean("USER_SESSION",true)
                    editor.putString("phone",phone)
                    editor.putString("pass",pass)
                    editor.commit()
                }

                override fun onFailure(call: Call<SignupModel>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }

            })


        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp!!.isLooping = true
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}