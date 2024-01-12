package com.example.giftshoppyproject_admin.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.giftshopproject.Client.ApiClient
import com.example.giftshoppyproject_admin.databinding.ActivityAddUserBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit


class AddUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUserBinding
    lateinit var apiinterface: Apiinterface
    lateinit var sharedPreferences: SharedPreferences
    lateinit var verificationid: String
    private lateinit var auth: FirebaseAuth
    lateinit var mcallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        auth = FirebaseAuth.getInstance()
        binding.edtOTP.visibility = View.GONE


        sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE)


        apiinterface = ApiClient.getapiclient().create(Apiinterface::class.java)


        binding.btnAddUser.setOnClickListener {
            if (TextUtils.isEmpty(binding.edtOTP.text.toString())) {
                Toast.makeText(applicationContext, "Please enter a valid OTP.", Toast.LENGTH_SHORT)
                    .show()
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
                binding.edtOTP.visibility = View.VISIBLE
            }
        }
        mcallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                var code = p0.smsCode

                if (code != null) {
                    binding.edtOTP.setText(code)
                } else {
                    Toast.makeText(applicationContext, "Error ", Toast.LENGTH_LONG).show();
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                p0.printStackTrace()
                Toast.makeText(applicationContext, "Failed " + p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                verificationid = p0
            }
        }
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

                var uname = binding.edtUserName.text.toString()
                var email = binding.edtEmail.text.toString()
                var phone = binding.edtPhone.text.toString()
                var password = binding.edtPassword.text.toString()

                if(uname.length == 0 && email.length == 0 && phone.length==0 && password.length ==0 )
                {
                    binding.edtUserName.setError("Please Enter First Name")
                    binding.edtEmail.setError("Please Enter Email")
                    binding.edtPhone.setError("Please Enter Phone")
                    binding.edtPassword.setError("Please Enter Password")
                    Toast.makeText(applicationContext, "Please Fill All Details", Toast.LENGTH_SHORT).show()
                }

                var call: Call<Void> = apiinterface.adduser(uname, email, phone, password)

                call.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {

                        Toast.makeText(applicationContext, "Added User Succesfully", Toast.LENGTH_LONG).show()

                        var i = Intent(applicationContext, DashboardActivity::class.java)
                        startActivity(i)
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(applicationContext, "Fail", Toast.LENGTH_LONG).show()
                    }
                })

            }
        }
            .addOnFailureListener {

                Toast.makeText(applicationContext,"Error", Toast.LENGTH_LONG).show()

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