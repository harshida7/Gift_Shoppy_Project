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
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.giftshopproject.Model.CategoryDetailModel
import com.example.giftshoppy_project.Activity.FullScreenImageActivity
import com.example.giftshoppy_project.R
import com.google.android.material.card.MaterialCardView
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso


class CategoryAdapter(var context: Context,var list: List<CategoryDetailModel>):RecyclerView.Adapter<MyView2>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView2
    {
        var  layoutInflater= LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.design_category,parent,false)
        return MyView2(view)

    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun isNightMode(context: Context): Boolean {
        val nightModeFlags =context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onBindViewHolder(holder: MyView2 , position: Int)
    {

        holder.textView.setText(list.get(position).product_name)
        Picasso.with(context).load(list.get(position).product_img).into(holder.imageview)

        holder.itemView.setOnClickListener {

            var i = Intent(context, FullScreenImageActivity::class.java)
            i.putExtra("id",list.get(position).id)
            i.putExtra("cid",list.get(position).c_id)
            i.putExtra("name",list.get(position).product_name)
            i.putExtra("price",list.get(position).product_price)
            i.putExtra("des",list.get(position).product_des)
            i.putExtra("image",list.get(position).product_img)
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

    }
}
class MyView2(Itemview:View):RecyclerView.ViewHolder(Itemview)
{
    var textView: TextView = Itemview.findViewById(R.id.text)
    var imageview: ImageView = Itemview.findViewById(R.id.img_category)
    var cardView:CardView= Itemview.findViewById(R.id.cardViewCategory)
    var mcardView:MaterialCardView= Itemview.findViewById(R.id.mcardView)

}