package com.example.giftshoppy_project.Activity

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.giftshopproject.Client.ApiClient
import com.example.giftshopproject.Interface.Apiinterface
import com.example.giftshoppy_project.R
import com.example.giftshoppy_project.databinding.ActivityFullScreenImageBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FullScreenImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullScreenImageBinding
    lateinit var apiinterface: Apiinterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityFullScreenImageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        apiinterface = ApiClient.getapiclient().create(Apiinterface::class.java)

        val DarkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
        val isDarkModeOn = DarkModeFlags == Configuration.UI_MODE_NIGHT_YES//Check if the Dark Mode is On

        if(isDarkModeOn)
        {
            binding.linearFullScreen.setBackgroundColor(Color.parseColor("#e4e5f1"))
          /*  binding.mcardViewMain.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.mcardViewMain.setStrokeColor(Color.parseColor("#990011"))*/
            binding.mcardViewPhoto.setStrokeColor(Color.parseColor("#990011"))
            binding.mcardViewPhoto.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        }
        else
        {
            binding.linearFullScreen.setBackgroundColor(Color.parseColor("#e4e5f1"))
           /* binding.mcardViewMain.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.mcardViewMain.setStrokeColor(Color.parseColor("#990011"))*/
            binding.mcardViewPhoto.setStrokeColor(Color.parseColor("#990011"))
            binding.mcardViewPhoto.setCardBackgroundColor(Color.parseColor("#FFFFFF"))

        }

        var i = intent

        var id = i.getIntExtra("id", 101)
        var cid= i.getIntExtra("cid",102)
        var price = i.getIntExtra("price", 0)
       var pname = i.getStringExtra("name")
       var pdes = i.getStringExtra("des")
        var image = i.getStringExtra("image")

        binding.txt1.setText("Name : "  + pname)
        binding.txt2.setText("Rs. " + price.toString())
        binding.txt3.setText(pdes)

        //binding.photoView.setImageResource(i.getStringExtra("image"))
        Picasso.with(applicationContext).load(image).into(binding.photoView)


        binding.btnAddWishlist.setOnClickListener {

            var call: Call<Void> =   apiinterface.addtowishlisht(cid,pname.toString(),price,image.toString(),pdes.toString())

            call.enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {

                    Toast.makeText(applicationContext,"Added to Wishlist", Toast.LENGTH_LONG).show()
                    startActivity(Intent(applicationContext,WishlistViewActivity::class.java))
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext,"Wishlist Fail", Toast.LENGTH_LONG).show()
                }
            })
        }

        binding.btnAddCart.setOnClickListener {
            var call: Call<Void> =   apiinterface.addtocart(cid,pname.toString(),price,image.toString(),pdes.toString())

            call.enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {

                    Toast.makeText(applicationContext,"Added to Cart", Toast.LENGTH_LONG).show()
                    startActivity(Intent(applicationContext,CartViewActivity::class.java))
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext,"Add to Cart Fail", Toast.LENGTH_LONG).show()
                }
            })
        }


    }
}