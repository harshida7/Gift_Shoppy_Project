package com.example.giftshoppy_project.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.giftshopproject.Client.ApiClient
import com.example.giftshopproject.Interface.Apiinterface
import com.example.giftshoppy_project.Activity.CartViewActivity
import com.example.giftshoppy_project.Activity.WishlistViewActivity
import com.example.giftshoppy_project.Model.WishlistModel
import com.example.giftshoppy_project.R
import com.github.chrisbanes.photoview.PhotoView
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishlistAdapter (var context: Context, var list: List<WishlistModel>):RecyclerView.Adapter<MyViewWishlist>()
{
    lateinit var apiinterface: Apiinterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewWishlist
    {
        var  layoutInflater= LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.design_wishlist,parent,false)
        return MyViewWishlist(view)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewWishlist, @SuppressLint("RecyclerView") position: Int)
    {


        holder.textView.setText("Name: " + list.get(position).product_name)
        holder.textView2.setText("Price: " + list.get(position).product_price.toString())


        var c_id = list.get(position).c_id

        Picasso.with(context).load(list.get(position).product_image).into(holder.imageview)


        apiinterface = ApiClient.getapiclient().create(Apiinterface::class.java)

        holder.imgCart.setOnClickListener {

            var call: Call<Void> =   apiinterface.addtocart(c_id,list.get(position).product_name,list.get(position).product_price,list.get(position).product_image,list.get(position).product_des)

            call.enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {

                    Toast.makeText(context,"Added to Cart", Toast.LENGTH_LONG).show()


                    Toast.makeText(context,"Added to Cart 1" + list.get(position).id, Toast.LENGTH_LONG).show()


                    var call: Call<WishlistModel> =  apiinterface.deletefromwishlist(list.get(position).id)

                    call!!.enqueue(object :Callback<WishlistModel>{
                        override fun onResponse(call: Call<WishlistModel>, response: Response<WishlistModel>) {
                            var i = Intent(context, WishlistViewActivity::class.java)
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(i)
                            notifyItemRangeChanged(position,0)
                        }

                        override fun onFailure(call: Call<WishlistModel>, t: Throwable) {
                            Toast.makeText(context,"Delete Fail", Toast.LENGTH_LONG).show()
                        }
                    })
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(context,"Cart Added Fail", Toast.LENGTH_LONG).show()
                }
            })
        }
        holder.imgDelete.setOnClickListener {

            var call: Call<WishlistModel> =  apiinterface.deletefromwishlist(list.get(position).id)


            call!!.enqueue(object: Callback<WishlistModel> {
                override fun onResponse(call: Call<WishlistModel>, response: Response<WishlistModel>) {

                    Toast.makeText(context, "Deleted From Wishlist Successfully", Toast.LENGTH_LONG).show()

                    var i = Intent(context, WishlistViewActivity::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(i)
                    notifyDataSetChanged()
                }

                override fun onFailure(call: Call<WishlistModel>, t: Throwable) {
                    Toast.makeText(context,"Delete Fail", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
class MyViewWishlist(Itemview: View): RecyclerView.ViewHolder(Itemview)
{

    var textView: TextView = Itemview.findViewById(R.id.txtProductName)
    var textView2: TextView = Itemview.findViewById(R.id.txtProductPrice)
    var imageview: ImageView = Itemview.findViewById(R.id.imgProduct)
    var imgCart:ImageView = Itemview.findViewById(R.id.btnImgCart)
    var imgDelete:ImageView = Itemview.findViewById(R.id.btnImgDelete)

}