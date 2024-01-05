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
import com.example.giftshoppy_project.Adapter.CartAddAdapter
import com.example.giftshoppy_project.Adapter.WishlistAdapter
import com.example.giftshoppy_project.Model.CartModel
import com.example.giftshoppy_project.Model.WishlistModel
import com.example.giftshoppy_project.R
import com.example.giftshoppy_project.databinding.ActivityCartViewBinding
import com.example.giftshoppy_project.databinding.ActivityWishlistViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartViewBinding
    lateinit var apiinterface: Apiinterface
    lateinit var call: Call<List<CartModel>>
    lateinit var list:MutableList<CartModel>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityCartViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        apiinterface=  ApiClient.getapiclient().create(Apiinterface::class.java)
        list = ArrayList()

        val DarkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
        val isDarkModeOn = DarkModeFlags == Configuration.UI_MODE_NIGHT_YES//Check if the Dark Mode is On

        if(isDarkModeOn)
        {
            binding.linearCartView.setBackgroundColor(Color.parseColor("#FBEAEB"))
            binding.btnPlaceOrder.setBackgroundColor(Color.parseColor("#990011"))
            binding.btnPlaceOrder.setTextColor(Color.parseColor("#ffffff"))

        }
        else
        {
            binding.linearCartView.setBackgroundColor(Color.parseColor("#FBEAEB"))
            binding.btnPlaceOrder.setBackgroundColor(Color.parseColor("#990011"))
            binding.btnPlaceOrder.setTextColor(Color.parseColor("#ffffff"))
        }


        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recycler.layoutManager=layoutManager



        var call: Call<List<CartModel>> =  apiinterface.cartviewdata()

        call.enqueue(object: Callback<List<CartModel>>
        {
            override fun onResponse(call: Call<List<CartModel>>, response: Response<List<CartModel>>) {


                list = response.body() as MutableList<CartModel>

                var cadapter = CartAddAdapter(applicationContext,list)
                binding.recycler.adapter=cadapter
            }

            override fun onFailure(call: Call<List<CartModel>>, t: Throwable) {

            }
        })



    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(applicationContext,DashboardActivity::class.java))
    }
}