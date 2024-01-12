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
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.example.giftshopproject.Client.ApiClient
import com.example.giftshoppyproject_admin.Adapter.CategroyViewAdapter
import com.example.giftshoppyproject_admin.Model.CategoryAddModel
import com.example.giftshoppyproject_admin.R
import com.example.giftshoppyproject_admin.databinding.ActivityViewCategoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewCategoryBinding
    lateinit var list:MutableList<CategoryAddModel>
    lateinit var apiinterface: Apiinterface
    lateinit var sharedPreferences: SharedPreferences
    lateinit var myAdapter:CategroyViewAdapter

    var map = HashMap<String,String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityViewCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE)

        list = ArrayList()

        registerForContextMenu(binding.recycler)

        var layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this,2)
        binding.recycler.layoutManager=layoutManager


        apiinterface=  ApiClient.getapiclient().create(Apiinterface::class.java)

        map.put("Chocolates", "https://compressible-approa.000webhostapp.com/Gift_Shoppy_Project_API/image/choco_cate.jpeg")
        map.put("Flowers", "https://compressible-approa.000webhostapp.com/Gift_Shoppy_Project_API/image/flowers_cat.jpeg")
        map.put("Toys & Games", "https://compressible-approa.000webhostapp.com/Gift_Shoppy_Project_API/image/toysGames.jpg")
        map.put("Jewellery", "https://compressible-approa.000webhostapp.com/Gift_Shoppy_Project_API/image/jewellery_cat.jpeg")
        map.put("God Idols", "https://compressible-approa.000webhostapp.com/Gift_Shoppy_Project_API/image/god%20idols.jpeg")
        map.put("Accessories", "https://compressible-approa.000webhostapp.com/Gift_Shoppy_Project_API/image/watches_cat.jpeg")

        for (i in map.keys) {
            var textslider = TextSliderView(this)
            textslider.description(i)
            textslider.image(map.get(i)!!)
            binding.imgSlider.addSlider(textslider)
        }
        binding.imgSlider.setPresetTransformer(SliderLayout.Transformer.Accordion)

        var call: Call<List<CategoryAddModel>> =  apiinterface.categoryviewdata()

        call.enqueue(object: Callback<List<CategoryAddModel>> {
            override fun onResponse(call: Call<List<CategoryAddModel>>, response: Response<List<CategoryAddModel>>) {

                Toast.makeText(applicationContext,"a", Toast.LENGTH_LONG).show()

                list = response.body() as MutableList<CategoryAddModel>

                myAdapter = CategroyViewAdapter(applicationContext,list)
                binding.recycler.adapter=myAdapter

            }

            override fun onFailure(call: Call<List<CategoryAddModel>>, t: Throwable) {
                Toast.makeText(applicationContext,"No Internet", Toast.LENGTH_LONG).show()
            }


        })

    }
}