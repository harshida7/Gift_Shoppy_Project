package com.example.giftshoppyproject_admin.Activity



import com.example.giftshopproject.Model.SignupModel
import com.example.giftshoppyproject_admin.Model.CategoryAddModel
import com.example.giftshoppyproject_admin.Model.ProductViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Apiinterface
{
    @FormUrlEncoded
    @POST("admin_add_user.php")
    fun adduser(
        @Field("admin_name") admin_name:String,
        @Field("admin_email") admin_email:String,
        @Field("admin_phone") admin_phone :String,
        @Field("admin_pass") admin_pass :String,

    ): Call<Void>


    @FormUrlEncoded
    @POST("admin_login.php")

    fun adminlogin(

        @Field("admin_email") admin_email:String,
        @Field("admin_pass") admin_pass:String,

        ): Call<SignupModel>


    @Multipart
    @POST("upload_category.php")
    suspend fun uploadcategory(

        @Part image1: MultipartBody.Part,
        @Part("category_name") category_name: RequestBody?,
        ): ResponseBody


    @GET("categoryview.php")
    fun categoryviewdata(): Call<List<CategoryAddModel>>


    @Multipart
    @POST("upload_product.php")
    suspend fun uploadproduct(

        @Part image1: MultipartBody.Part,
        @Part("c_id") c_id : RequestBody?,
        @Part("product_name") product_name: RequestBody?,
        @Part("product_price") product_price: RequestBody?,
        @Part("product_des") product_des : RequestBody?,

    ): ResponseBody

    @FormUrlEncoded
    @POST("productview.php")
    fun productviewdata( @Field("data") data:Int): Call<List<ProductViewModel>>


    @FormUrlEncoded
    @POST("category_update.php")
    fun updatecategory
                (
        @Field("id") id:Int,
        @Field("category_name") category_name:String,
        @Field("category_img")  category_img:String,

        ) : Call<Void>


    @FormUrlEncoded
    @POST("delete_category.php")
    fun deletecategory(@Field("id") id: Int): Call<Void>
}