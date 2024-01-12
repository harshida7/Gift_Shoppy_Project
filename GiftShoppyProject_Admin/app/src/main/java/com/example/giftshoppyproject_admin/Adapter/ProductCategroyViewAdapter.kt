package com.example.giftshoppyproject_admin.Adapter

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.giftshoppyproject_admin.Activity.Apiinterface
import com.example.giftshoppyproject_admin.Activity.ViewProductActivity
import com.example.giftshoppyproject_admin.Model.CategoryAddModel
import com.example.giftshoppyproject_admin.Model.ProductViewModel
import com.example.giftshoppyproject_admin.R
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso



class ProductCategroyViewAdapter(var context: Context,var list: List<ProductViewModel>):RecyclerView.Adapter<MyViewProduct>()
{
    lateinit var apiinterface: Apiinterface




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewProduct
    {
        var  layoutInflater= LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.design_product_view,parent,false)
        return MyViewProduct(view)

    }

    override fun onBindViewHolder(holder: MyViewProduct, position: Int) {

        holder.textView.setText(list.get(position).product_name)
        Picasso.with(context).load(list.get(position).product_img).placeholder(R.mipmap.ic_launcher).into(holder.imageview)
        /*holder.itemView.setOnClickListener {

            var i = Intent(context,ViewProductActivity::class.java)
            i.putExtra("mypos",list.get(position).id)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)


        }*/
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
class MyViewProduct(Itemview:View):RecyclerView.ViewHolder(Itemview)
{
    var textView: TextView = Itemview.findViewById(R.id.text)
    var imageview: CircularImageView = Itemview.findViewById(R.id.circularImageView)

}