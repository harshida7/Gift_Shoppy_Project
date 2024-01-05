package com.example.giftshoppy_project.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.CompoundButton
import android.widget.Toast
import com.example.giftshopproject.Client.ApiClient
import com.example.giftshopproject.Interface.Apiinterface
import com.example.giftshoppy_project.R
import com.example.giftshoppy_project.databinding.ActivitySignUpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class SignUpActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener,
        MediaPlayer.OnPreparedListener {

        private lateinit var binding: ActivitySignUpBinding
        lateinit var apiinterface: Apiinterface
        var gender = ""
        lateinit var sharedPreferences: SharedPreferences
        lateinit var verificationid: String
        private lateinit var auth: FirebaseAuth
        lateinit var mcallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            binding = ActivitySignUpBinding.inflate(layoutInflater)
            val view = binding.root

            setContentView(view)

            auth = FirebaseAuth.getInstance()
            binding.edtOTP.visibility =  View.INVISIBLE

            val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.vide)

            binding.videoView.setVideoURI(uri)
            binding.videoView.start()

            binding.videoView.setOnPreparedListener(this)

            val DarkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
            val isDarkModeOn = DarkModeFlags == Configuration.UI_MODE_NIGHT_YES//Check if the Dark Mode is On

            if(isDarkModeOn)
            {
                binding.edtPhone.setTextColor(Color.parseColor("#00203F"))
            }

            sharedPreferences = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)

            if (sharedPreferences.getBoolean("USER_SESSION", false) && !sharedPreferences.getString("phone", "")!!.isEmpty()) {

                startActivity(Intent(applicationContext, DashboardActivity::class.java))

                finish()
            }
            apiinterface = ApiClient.getapiclient().create(Apiinterface::class.java)

            binding.textView3.setOnClickListener {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }

            binding.rbMale.setOnCheckedChangeListener(this)
            binding.rbFemale.setOnCheckedChangeListener(this)

            binding.btnsignup.setOnClickListener {
                if (TextUtils.isEmpty(binding.edtOTP.text.toString())) {
                    Toast.makeText(applicationContext, "Please enter a valid OTP.", Toast.LENGTH_SHORT).show()
                } else {
                    val otp: String = binding.edtOTP.text.toString()
                    verifycode(otp)
                }

            }

            binding.btnVerify.setOnClickListener {
                if (TextUtils.isEmpty(binding.edtPhone.text.toString())) {

                    Toast.makeText(applicationContext, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show()
                } else {

                    var mob = binding.edtPhone.text.toString()

                    sendverificationcode(mob)
                    binding.edtOTP.visibility =  View.VISIBLE
                }
            }
            mcallback= object :PhoneAuthProvider.OnVerificationStateChangedCallbacks()
            {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                    var code = p0.smsCode

                    if (code != null) {
                        binding.edtOTP.setText(code)
                    } else {
                        Toast.makeText(applicationContext, "Error ", Toast.LENGTH_LONG).show();
                    }
                }

                override fun onVerificationFailed(p0: FirebaseException)
                {
                    p0.printStackTrace()
                    Toast.makeText(applicationContext,"Failed "  + p0.message,Toast.LENGTH_LONG).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken)
                {
                    verificationid=p0
                }
            }
        }
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if (binding.rbMale.isChecked)
                {
                    gender = "Male"
                }
                if (binding.rbFemale.isChecked)
                {
                    gender = "Female"
                }
            }

        override fun onPrepared(mp: MediaPlayer?) {
            mp!!.isLooping=true
        }

        private fun verifycode(otp: String)
        {
            val credential = PhoneAuthProvider.getCredential(verificationid, otp)
            signinwithcredential(credential)
        }

        private fun signinwithcredential(credential: PhoneAuthCredential)
        {

            auth.signInWithCredential(credential).addOnCompleteListener {

                if(it.isSuccessful)
                {

                    var fname = binding.edtFirstName.text.toString()
                    var lname = binding.edtLastName.text.toString()
                    var email = binding.edtEmail.text.toString()
                    var phone = binding.edtPhone.text.toString()
                    var password = binding.edtPassword.text.toString()

                    if(fname.length == 0 && lname.length == 0 && email.length==0 && phone.length == 0 && password.length ==0 )
                    {
                        binding.edtFirstName.setError("Please Enter First Name")
                        binding.edtLastName.setError("Please Enter Last Name")
                        binding.edtEmail.setError("Please Enter Email")
                        binding.edtPhone.setError("Please Enter Phone")
                        binding.edtPassword.setError("Please Enter Password")
                        Toast.makeText(applicationContext, "Please Fill All Details", Toast.LENGTH_SHORT).show()
                    }

                    var call: Call<Void> =
                        apiinterface.signupuser(fname, lname, gender, email, phone, password)

                    call.enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {

                            Toast.makeText(applicationContext, "Signup Succesful", Toast.LENGTH_LONG).show()

                            var i = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(i)
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(applicationContext, "Fail", Toast.LENGTH_LONG).show()
                        }
                    })

                }
            }
                .addOnFailureListener {

                    Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()

                }
        }
        private fun sendverificationcode(mob: String)
        {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(mob,60, TimeUnit.SECONDS,this,mcallback)
        }

        override fun onBackPressed() {
            super.onBackPressed()
            finishAffinity()
        }

    }