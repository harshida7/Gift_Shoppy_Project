package com.example.giftshoppy_project.Adapter

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.giftshopproject.Interface.Apiinterface
import com.example.giftshoppy_project.Activity.CategoryProductActivity
import com.example.giftshoppy_project.Model.CategoryAddModel
import com.example.giftshoppy_project.R
import com.google.android.material.card.MaterialCardView

import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso


class DashboardCategroyAddAdapter(var context: Context,var list: List<CategoryAddModel>):RecyclerView.Adapter<MyViewDashboard>()
{
    lateinit var apiinterface: Apiinterface
    val filteredlist = ArrayList<CategoryAddModel>()

    init {
        filteredlist.addAll(list)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewDashboard
    {
        var  layoutInflater= LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.design_dashboard,parent,false)
        return MyViewDashboard(view)

    }

    override fun getItemCount(): Int {
        return filteredlist.size
    }
    fun isNightMode(context: Context): Boolean {
        val nightModeFlags =context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onBindViewHolder(holder: MyViewDashboard , position: Int)
    {

        holder.textView.setText(list.get(position).category_name)
        Picasso.with(context).load(list.get(position).category_img).into(holder.imageview)
        holder.itemView.setOnClickListener {

            var i = Intent(context,CategoryProductActivity::class.java)
            i.putExtra("mypos",list.get(position).id)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)

        }


        if(isNightMode(context))
        {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            holder.mcardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            holder.mcardView.setStrokeColor(Color.parseColor("#990011"))
        }
        else
        {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            holder.mcardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            holder.mcardView.setStrokeColor(Color.parseColor("#990011"))
        }
        val item = filteredlist[position]
        holder.textView.text = item.category_name
        Picasso.with(context).load(list.get(position).category_img).placeholder(R.mipmap.ic_launcher).into(holder.imageview)
    }

    fun filter(query : String){

        filteredlist.clear()

        if(query.isEmpty())
        {
            filteredlist.addAll(list)
        }

        else
        {
            val lowercasequery = query.toLowerCase()

            for(item in list)
            {
                if(item.category_name.toLowerCase().contains(lowercasequery))
                {
                    filteredlist.add(item)
                }
            }

        }
        notifyDataSetChanged()
    }

}
class MyViewDashboard(Itemview:View):RecyclerView.ViewHolder(Itemview)
{
    var textView: TextView = Itemview.findViewById(R.id.text)
    var imageview: ImageView = Itemview.findViewById(R.id.img_category)
    var cardView:CardView= Itemview.findViewById(R.id.cardViewDashboard)
    var mcardView:MaterialCardView= Itemview.findViewById(R.id.mcardView)



}