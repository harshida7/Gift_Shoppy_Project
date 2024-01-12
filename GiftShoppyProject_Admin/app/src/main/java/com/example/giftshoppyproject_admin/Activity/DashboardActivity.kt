package com.example.giftshoppyproject_admin.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giftshoppyproject_admin.Adapter.DashboardAdapter
import com.example.giftshoppyproject_admin.Model.DashboardModel
import com.example.giftshoppyproject_admin.R
import com.example.giftshoppyproject_admin.databinding.ActivityAddUserBinding
import com.example.giftshoppyproject_admin.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerView: RecyclerView
    lateinit var list:MutableList<DashboardModel>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.tool1)

        sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE)

        recyclerView = findViewById(R.id.recycler)
        list = ArrayList()

        //listview format
        /* var manager : RecyclerView.LayoutManager = LinearLayoutManager(this)
         recyclerView.layoutManager=manager*/

        //gridview format
        var manager : RecyclerView.LayoutManager = GridLayoutManager(this,2)
        recyclerView.layoutManager=manager

        list.add(DashboardModel(R.drawable.adduser,"Add User"))
        list.add(DashboardModel(R.drawable.addcategory,"Add Category"))
        list.add(DashboardModel(R.drawable.addproduct,"Add Product"))
        list.add(DashboardModel(R.drawable.viewcategory,"View Category"))
        list.add(DashboardModel(R.drawable.information,"About us"))

        var myAdapter = DashboardAdapter(applicationContext,list)
        recyclerView.adapter=myAdapter


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.option,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.logout->
            {
                sharedPreferences.edit().clear().commit()
                finish()
                startActivity(Intent(applicationContext,LoginActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}