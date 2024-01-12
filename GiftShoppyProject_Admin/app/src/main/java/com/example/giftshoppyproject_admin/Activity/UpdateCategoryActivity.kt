package com.example.giftshoppyproject_admin.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.giftshopproject.Client.ApiClient
import com.example.giftshoppyproject_admin.Model.CategoryAddModel
import com.example.giftshoppyproject_admin.R
import com.example.giftshoppyproject_admin.databinding.ActivityAddCategoryBinding
import com.example.giftshoppyproject_admin.databinding.ActivityUpdateCategoryBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream

class UpdateCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateCategoryBinding


    lateinit var imageuri: Uri
    lateinit var sharedPreferences: SharedPreferences
    lateinit var apiinterface: Apiinterface

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent())
    {
        imageuri = it!!
        binding.imgCategoryUpdate.setImageURI(it)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        apiinterface = ApiClient.getapiclient().create(Apiinterface::class.java)
        var i = intent
        var id = i.getIntExtra("id", 101)
        var cname = i.getStringExtra("cname")
        var cimg = i.getStringExtra("cimg")

        binding.edtCategoryNameUpdate.setText(cname)
       Picasso.with(applicationContext).load(cimg).placeholder(R.mipmap.ic_launcher).into(binding.imgCategoryUpdate)

        sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        binding.imgCategoryUpdate.setOnClickListener { contract.launch("image/*") }

        binding.btnAddCategory.setOnClickListener {

            var call: Call<Void> = apiinterface.updatecategory(id, binding.edtCategoryNameUpdate.text.toString(), binding.imgCategoryUpdate.toString())
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
            uploadCategory()
                    Toast.makeText(applicationContext, "Category Updated", Toast.LENGTH_SHORT).show()
                    val i = Intent(applicationContext, ViewCategoryActivity::class.java)
                    startActivity(i)
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show();
                }
            })

        }
    }

    private fun uploadCategory() {

        var m = CategoryAddModel()
        val filesDir = applicationContext.filesDir
        val file = File(filesDir, "image.png")
        val inputstream = contentResolver.openInputStream(imageuri)
        val outputstream = FileOutputStream(file)
        inputstream!!.copyTo(outputstream)

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

        val part = MultipartBody.Part.createFormData("category_img", file.name, requestBody)

        val cname: RequestBody =
            RequestBody.Companion.create(MultipartBody.FORM, binding.edtCategoryNameUpdate.text.toString())


        val retrofit = Retrofit.Builder()
            .baseUrl("https://compressible-approa.000webhostapp.com/Gift_Shoppy_Project_API/Admin_API/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Apiinterface::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.uploadcategory(part, cname)
        }

    }
}