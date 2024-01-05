package com.example.giftshoppy_project.Activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.giftshoppy_project.R
import com.example.giftshoppy_project.databinding.ActivityPaymentBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(), PaymentResultListener {

    private lateinit var binding: ActivityPaymentBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val i = intent
        val id = i.getIntExtra("id", 0)
        val name = i.getStringExtra("name")
        val description = i.getStringExtra("description")
        val price = i.getIntExtra("price",101)
        val image = i.getStringExtra("image")
        sharedPreferences = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)



        binding.giftName.text = name
        binding.giftDesc.text = description
        binding.giftPrice.text = price.toString()
        Picasso.with(applicationContext).load(image).into(binding.imageView)

        binding.makePaymentBtn.setOnClickListener{

            val pPrice = Integer.parseInt(price.toString())*100
            val checkout = Checkout()
            checkout.setKeyID("rzp_test_pJ8ElvmChXIyZC")
            val obj = JSONObject()
            try {
                obj.put("name", name)
                obj.put("description", "Test Payment")
                obj.put("theme.color", "")
                obj.put("currency", "INR")
                obj.put("amount", pPrice)
                obj.put("prefill.contact", sharedPreferences.getString("phone", ""))
                obj.put("prefill.email", "jchirag2000@gmail.com")
                checkout.open(this, obj)
            } catch (e: JSONException) {
                e.printStackTrace()
            }



        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(applicationContext, "Payment Success $p0", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(applicationContext, "Payment Failed $p0 : $p1", Toast.LENGTH_SHORT).show()
    }


}


