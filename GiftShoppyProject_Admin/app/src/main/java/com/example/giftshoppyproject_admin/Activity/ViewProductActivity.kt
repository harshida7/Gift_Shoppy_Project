package com.example.giftshoppyproject_admin.Activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giftshopproject.Client.ApiClient
import com.example.giftshoppyproject_admin.Adapter.CategroyViewAdapter
import com.example.giftshoppyproject_admin.Adapter.ProductCategroyViewAdapter
import com.example.giftshoppyproject_admin.Model.CategoryAddModel
import com.example.giftshoppyproject_admin.Model.ProductViewModel
import com.example.giftshoppyproject_admin.R
import com.example.giftshoppyproject_admin.databinding.ActivityViewCategoryBinding
import com.example.giftshoppyproject_admin.databinding.ActivityViewProductBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewProductBinding
    lateinit var list:MutableList<ProductViewModel>
    lateinit var apiinterface: Apiinterface
    lateinit var sharedPreferences: SharedPreferences
    lateinit var myAdapter: ProductCategroyViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityViewProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE)

        list = ArrayList()

        var i = intent
        var id = i.getIntExtra("mypos",102)

        var layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this,2)
        binding.recycler.layoutManager=layoutManager


        apiinterface=  ApiClient.getapiclient().create(Apiinterface::class.java)

        var call: Call<List<ProductViewModel>> =  apiinterface.productviewdata(id)

        call.enqueue(object: Callback<List<ProductViewModel>> {
            override fun onResponse(call: Call<List<ProductViewModel>>, response: Response<List<ProductViewModel>>) {

                Toast.makeText(applicationContext,"a", Toast.LENGTH_LONG).show()

                list = response.body() as MutableList<ProductViewModel>

                myAdapter = ProductCategroyViewAdapter(applicationContext,list)
                binding.recycler.adapter=myAdapter

            }

            override fun onFailure(call: Call<List<ProductViewModel>>, t: Throwable) {
                Toast.makeText(applicationContext,"No Internet", Toast.LENGTH_LONG).show()
            }


        })

    }
}