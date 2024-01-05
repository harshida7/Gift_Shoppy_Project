package com.example.giftshopproject.Interface


import com.example.giftshopproject.Model.CategoryDetailModel
import com.example.giftshopproject.Model.SignupModel
import com.example.giftshoppy_project.Model.CartModel
import com.example.giftshoppy_project.Model.CategoryAddModel
import com.example.giftshoppy_project.Model.WishlistModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Apiinterface
{
    @FormUrlEncoded
    @POST("user_signup.php")
    fun signupuser(
        @Field("first_name") first_name:String,
        @Field("last_name") last_name:String,
        @Field("gender") gender:String,
        @Field("email") email:String,
        @Field("phone") phone:String,
        @Field("password") password:String,
            ): Call<Void>

    @FormUrlEncoded
    @POST("user_login.php")

    fun loginuser(

        @Field("phone") phone:String,
        @Field("password") password:String,

         ): Call<SignupModel>

    @FormUrlEncoded
    @POST("admin_login.php")

    fun adminlogin(

        @Field("admin_email") admin_email:String,
        @Field("admin_pass") admin_pass:String,

        ): Call<SignupModel>


    @FormUrlEncoded
    @POST("profile.php")
    fun viewData(

        @Field("phone") phone:String,

         ) : Call<Void>


    @FormUrlEncoded
    @POST("user_profile_update.php")

    fun updateUserProfile
                (
                    @Field("id") id:Int,
                    @Field("first_name") first_name:String,
                    @Field("email") email:String,
                    @Field("phone") phone:String,

                    ) : Call<Void>

    @GET("categoryview.php")
    fun categoryviewdata(): Call<List<CategoryAddModel>>

    @FormUrlEncoded
    @POST("category_imagesview.php")
    fun categoryimageviewdata( @Field("data") data:Int): Call<List<CategoryDetailModel>>


    @FormUrlEncoded
    @POST("add_to_wishlist.php")
    fun addtowishlisht(
        @Field("c_id") c_id:Int,
        @Field("product_name") product_name:String,
        @Field("product_price") product_price:Int,
        @Field("product_image") product_image:String,
        @Field("product_des") product_des:String,

    ): Call<Void>

    @GET("wishlist_view.php")
    fun wishlistviewdata(): Call<List<WishlistModel>>


    @FormUrlEncoded
    @POST("add_to_cart.php")
    fun addtocart(
        @Field("c_id") c_id:Int,
        @Field("product_name") product_name:String,
        @Field("product_price") product_price:Int,
        @Field("product_image") product_image:String,
        @Field("product_des") product_des:String,

        ): Call<Void>

    @GET("cart_view.php")
    fun cartviewdata(): Call<List<CartModel>>

    @FormUrlEncoded
    @POST("delete_wishlist.php")
    fun deletefromwishlist(@Field("id") id: Int): Call<WishlistModel>
}