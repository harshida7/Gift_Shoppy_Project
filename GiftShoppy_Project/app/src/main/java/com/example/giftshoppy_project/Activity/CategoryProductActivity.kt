package com.example.giftshoppy_project.Activity

import android.R
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giftshopproject.Client.ApiClient
import com.example.giftshopproject.Interface.Apiinterface
import com.example.giftshopproject.Model.CategoryDetailModel
import com.example.giftshoppy_project.Adapter.CategoryAdapter
import com.example.giftshoppy_project.databinding.ActivityCategoryProductBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoryProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryProductBinding
    lateinit var apiinterface: Apiinterface
    lateinit var call: Call<List<CategoryDetailModel>>
    lateinit var list:MutableList<CategoryDetailModel>
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityCategoryProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val DarkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
        val isDarkModeOn = DarkModeFlags == Configuration.UI_MODE_NIGHT_YES//Check if the Dark Mode is On

        if(isDarkModeOn) { binding.linearCategoryProduct.setBackgroundColor(Color.parseColor("#FBEAEB")) }
        else { binding.linearCategoryProduct.setBackgroundColor(Color.parseColor("#FBEAEB")) }

        apiinterface=  ApiClient.getapiclient().create(Apiinterface::class.java)
        list = ArrayList()

        var i = intent
            var id = i.getIntExtra("mypos",102)

        var layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext,2)
        binding.recycler.layoutManager=layoutManager




        var call:Call<List<CategoryDetailModel>> =  apiinterface.categoryimageviewdata(id)

        call.enqueue(object: Callback<List<CategoryDetailModel>>
        {
            override fun onResponse(call: Call<List<CategoryDetailModel>>, response: Response<List<CategoryDetailModel>>) {


                list = response.body() as MutableList<CategoryDetailModel>

                var cadapter = CategoryAdapter(applicationContext,list)
                binding.recycler.adapter=cadapter
            }

            override fun onFailure(call: Call<List<CategoryDetailModel>>, t: Throwable) {

            }
        })



    }
}