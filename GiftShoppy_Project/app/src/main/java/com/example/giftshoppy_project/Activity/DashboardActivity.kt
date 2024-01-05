package com.example.giftshoppy_project.Activity

import android.R.attr.name
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.DefaultSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.example.giftshopproject.Client.ApiClient
import com.example.giftshopproject.Interface.Apiinterface
import com.example.giftshoppy_project.Adapter.DashboardCategroyAddAdapter
import com.example.giftshoppy_project.Model.CategoryAddModel
import com.example.giftshoppy_project.R
import com.example.giftshoppy_project.databinding.ActivityDashboardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashboardActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    BaseSliderView.OnSliderClickListener {
    private lateinit var binding: ActivityDashboardBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var list:MutableList<CategoryAddModel>
    lateinit var apiinterface: Apiinterface
    lateinit var myAdapter:DashboardCategroyAddAdapter
    var map = HashMap<String,String>()

    lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        swipeContainer =  findViewById(R.id.swipeContainer)


        setSupportActionBar(binding.tool1)
        val DarkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
        val isDarkModeOn = DarkModeFlags == Configuration.UI_MODE_NIGHT_YES//Check if the Dark Mode is On

        if(isDarkModeOn)
        {
            binding.linearDashboard.setBackgroundColor(Color.parseColor("#FBEAEB"))
            binding.tool1.setBackgroundColor(Color.parseColor("#990011"))

        }
        else
        {
            binding.linearDashboard.setBackgroundColor(Color.parseColor("#FBEAEB"))
            binding.tool1.setBackgroundColor(Color.parseColor("#990011"))

        }
        sharedPreferences = getSharedPreferences("USER_SESSION",Context.MODE_PRIVATE)

        Toast.makeText(applicationContext,"Welcome "+sharedPreferences.getString("phone","null"),Toast.LENGTH_LONG).show()


        list = ArrayList()


        var layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this,2)
        binding.recycler.layoutManager=layoutManager

        apiinterface=  ApiClient.getapiclient().create(Apiinterface::class.java)

        map.put("Cakes", "https://compressible-approa.000webhostapp.com/Gift_Shoppy_Project_API/image/cake_cate.jpg")
        map.put("Flowers", "https://compressible-approa.000webhostapp.com/Gift_Shoppy_Project_API/image/flowers_cate.jpg")
        map.put("Teddy Bears", "https://compressible-approa.000webhostapp.com/Gift_Shoppy_Project_API/image/teddy_cate.jpg")
        map.put("Jewellery", "https://compressible-approa.000webhostapp.com/Gift_Shoppy_Project_API/image/jwellery_cate.jpg")
        map.put("God Idols", "https://compressible-approa.000webhostapp.com/Gift_Shoppy_Project_API/image/god_cate.jpg")
        map.put("Gift Vouchers", "https://compressible-approa.000webhostapp.com/Gift_Shoppy_Project_API/image/voucher_cate.jpg")

        for (i in map.keys) {

            val defaultSliderView = DefaultSliderView(applicationContext)
            defaultSliderView.image(map.get(i))
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .setOnSliderClickListener(this)
            binding.imgSlider.addSlider(defaultSliderView)
        }
        binding.imgSlider.setPresetTransformer(SliderLayout.Transformer.Accordion)


        swipeContainer.setOnRefreshListener(this)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option, menu)
        val mSearch = menu.findItem(R.id.search1)
        val mSearchView = mSearch.actionView as SearchView?
        mSearchView!!.queryHint = "Search"
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                myAdapter.filter(newText.orEmpty())
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {

            R.id.logout->
            {
                sharedPreferences.edit().clear().commit()

                finish()
                startActivity(Intent(applicationContext,SignUpActivity::class.java))
            }

            R.id.wishlist->
            {
                startActivity(Intent(applicationContext,WishlistViewActivity::class.java))
            }
            R.id.cart->
            {
                Toast.makeText(applicationContext, "Cart Selected", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext,CartViewActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onRefresh() {
        var call: Call<List<CategoryAddModel>> =  apiinterface.categoryviewdata()

        call.enqueue(object: Callback<List<CategoryAddModel>> {
            override fun onResponse(call: Call<List<CategoryAddModel>>, response: Response<List<CategoryAddModel>>) {


                list = response.body() as MutableList<CategoryAddModel>

                myAdapter = DashboardCategroyAddAdapter(applicationContext,list)
                binding.recycler.adapter=myAdapter

                swipeContainer.isRefreshing = false
            }

            override fun onFailure(call: Call<List<CategoryAddModel>>, t: Throwable) {
                Toast.makeText(applicationContext,"No Internet",Toast.LENGTH_LONG).show()

                swipeContainer.isRefreshing = false
            }


        })
    }

    override fun onSliderClick(slider: BaseSliderView?) {
        TODO("Not yet implemented")
    }
}