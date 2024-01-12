package com.example.giftshopproject.Client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient
{
    companion object
    {
        var BASE_URL = "https://compressible-approa.000webhostapp.com/Gift_Shoppy_Project_API/Admin_API/"
        lateinit var retrofit: Retrofit

        fun getapiclient() : Retrofit
        {
            retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }
    }
}