package com.example.giftshoppy_project.Activity

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giftshopproject.Client.ApiClient
import com.example.giftshopproject.Interface.Apiinterface
import com.example.giftshoppy_project.Adapter.WishlistAdapter
import com.example.giftshoppy_project.Model.WishlistModel
import com.example.giftshoppy_project.R
import com.example.giftshoppy_project.databinding.ActivityWishlistViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishlistViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWishlistViewBinding
    lateinit var apiinterface: Apiinterface
    lateinit var call: Call<List<WishlistModel>>
    lateinit var list:MutableList<WishlistModel>
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityWishlistViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        apiinterface=  ApiClient.getapiclient().create(Apiinterface::class.java)
        list = ArrayList()
        val DarkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
        val isDarkModeOn = DarkModeFlags == Configuration.UI_MODE_NIGHT_YES//Check if the Dark Mode is On

        if(isDarkModeOn)
        {
            binding.linearWishlist.setBackgroundColor(Color.parseColor("#FBEAEB"))
        }
        else
        {
            binding.linearWishlist.setBackgroundColor(Color.parseColor("#FBEAEB"))
        }


        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recycler.layoutManager=layoutManager



        var call:Call<List<WishlistModel>> =  apiinterface.wishlistviewdata()

        call.enqueue(object: Callback<List<WishlistModel>>
        {
            override fun onResponse(call: Call<List<WishlistModel>>, response: Response<List<WishlistModel>>) {


                list = response.body() as MutableList<WishlistModel>

                var cadapter = WishlistAdapter(applicationContext,list)
                binding.recycler.adapter=cadapter
            }

            override fun onFailure(call: Call<List<WishlistModel>>, t: Throwable) {

            }
        })



    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(applicationContext,DashboardActivity::class.java))
    }
}