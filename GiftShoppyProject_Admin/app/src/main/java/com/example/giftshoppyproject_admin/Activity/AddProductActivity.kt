package com.example.giftshoppyproject_admin.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.giftshopproject.Client.ApiClient
import com.example.giftshoppyproject_admin.Adapter.CategroyViewAdapter
import com.example.giftshoppyproject_admin.Adapter.SpinnerDataAdapter
import com.example.giftshoppyproject_admin.Model.CategoryAddModel
import com.example.giftshoppyproject_admin.databinding.ActivityAddProductBinding
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

class AddProductActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityAddProductBinding

    lateinit var imageuri: Uri
    lateinit var sharedPreferences: SharedPreferences
    lateinit var myAdapter: CategroyViewAdapter
    var map = HashMap<String,String>()
    lateinit var apiinterface: Apiinterface
    lateinit var list:MutableList<CategoryAddModel>
    private val contract = registerForActivityResult(ActivityResultContracts.GetContent())
    {
        imageuri = it!!
        binding.imgCategory.setImageURI(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        list = ArrayList()
        val selection = "Category"


        sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE)

        apiinterface=  ApiClient.getapiclient().create(Apiinterface::class.java)
        var call: Call<List<CategoryAddModel>> =  apiinterface.categoryviewdata()

        call.enqueue(object: Callback<List<CategoryAddModel>> {
            override fun onResponse(call: Call<List<CategoryAddModel>>, response: Response<List<CategoryAddModel>>) {

                Toast.makeText(applicationContext,"a", Toast.LENGTH_LONG).show()

                list = response.body() as MutableList<CategoryAddModel>

                var adapter = SpinnerDataAdapter(applicationContext,list)
                binding.spin.adapter=adapter


               // var data = arrayOf(list)

                //val adap =ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item,data)
                //adap.setDropDownViewResource(R.layout.design_spinner)
                //binding.spin.adapter = adap

                //list = response.body() as MutableList<CategoryAddModel>

                //myAdapter = CategroyViewAdapter(applicationContext,list)
                //binding.recycler.adapter=myAdapter

            }

            override fun onFailure(call: Call<List<CategoryAddModel>>, t: Throwable) {
                Toast.makeText(applicationContext,"No Internet", Toast.LENGTH_LONG).show()
            }


        })


        binding.imgCategory.setOnClickListener { contract.launch("image/*") }

        binding.btnAddProduct.setOnClickListener {

            uploadProduct()

            startActivity(Intent(applicationContext,DashboardActivity::class.java))

            Toast.makeText(applicationContext, "Product Added", Toast.LENGTH_SHORT).show()
        }
    }




    private fun uploadProduct() {

        var m = CategoryAddModel()
        val filesDir = applicationContext.filesDir
        val file = File(filesDir, "image.png")
        val inputstream = contentResolver.openInputStream(imageuri)
        val outputstream = FileOutputStream(file)
        inputstream!!.copyTo(outputstream)

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

        val part = MultipartBody.Part.createFormData("product_img", file.name, requestBody)

        val cid: RequestBody =
            RequestBody.Companion.create(MultipartBody.FORM, binding.spin.selectedItemPosition.plus(1).toString())
        val pname: RequestBody =
            RequestBody.Companion.create(MultipartBody.FORM, binding.edtProductName.text.toString())
        val pprice: RequestBody =
            RequestBody.Companion.create(MultipartBody.FORM, binding.edtProductPrice.text.toString())
        val pdes: RequestBody =
            RequestBody.Companion.create(MultipartBody.FORM, binding.edtProductDes.text.toString())

        val retrofit = Retrofit.Builder()
            .baseUrl("https://compressible-approa.000webhostapp.com/Gift_Shoppy_Project_API/Admin_API/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Apiinterface::class.java)
       /* Toast.makeText(applicationContext, "pos " + binding.spin.selectedItemPosition.plus(1).toString(), Toast.LENGTH_SHORT).show()*/

        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.uploadproduct(part, cid,pname,pprice,pdes)
        }

    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(applicationContext, " " + position, Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
    }
}