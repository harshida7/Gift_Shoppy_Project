package com.example.giftshoppyproject_admin.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.giftshoppyproject_admin.Model.CategoryAddModel
import com.example.giftshoppyproject_admin.Model.DashboardModel
import com.example.giftshoppyproject_admin.R
import com.example.giftshoppyproject_admin.databinding.ActivityAddCategoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream

class AddCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCategoryBinding


    lateinit var imageuri: Uri
    lateinit var sharedPreferences: SharedPreferences

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent())
    {
        imageuri = it!!
        binding.imgCategory.setImageURI(it)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        binding.imgCategory.setOnClickListener { contract.launch("image/*") }

        binding.btnAddCategory.setOnClickListener {

            uploadCategory()

            startActivity(Intent(applicationContext,DashboardActivity::class.java))

            Toast.makeText(applicationContext, "Category Added", Toast.LENGTH_SHORT).show()
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
            RequestBody.Companion.create(MultipartBody.FORM, binding.edtCategoryName.text.toString())


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