package com.example.giftshoppyproject_admin.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.giftshoppyproject_admin.Activity.AboutUsActivity
import com.example.giftshoppyproject_admin.Activity.AddProductActivity
import com.example.giftshoppyproject_admin.Activity.AddUserActivity
import com.example.giftshoppyproject_admin.Activity.ViewCategoryActivity
import com.example.giftshoppyproject_admin.Activity.ViewProductActivity
import com.example.giftshoppyproject_admin.Activity.AddCategoryActivity
import com.example.giftshoppyproject_admin.Model.DashboardModel
import com.example.giftshoppyproject_admin.R
import com.mikhaellopez.circularimageview.CircularImageView

class DashboardAdapter(var context: Context, var list:MutableList<DashboardModel>) : RecyclerView.Adapter<MyViewDashboard>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewDashboard
    {
        var inflater = LayoutInflater.from(context)
        var view = inflater.inflate(R.layout.design_dashboard,parent,false)
        return MyViewDashboard(view)
    }
    override fun onBindViewHolder(holder: MyViewDashboard, position: Int)
    {
        holder.image.setImageResource(list.get(position).image)
        holder.txt.setText(list.get(position).text)


        holder.itemView.setOnClickListener{
            Toast.makeText(context, "" + position, Toast.LENGTH_LONG).show()
            if(position == 0)
            {
                val myactivity = Intent(context.applicationContext, AddUserActivity::class.java)
                myactivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.applicationContext.startActivity(myactivity)
            }
            if(position == 1)
            {
                val myactivity = Intent(context.applicationContext, AddCategoryActivity::class.java)
                myactivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.applicationContext.startActivity(myactivity)
            }
            if(position == 2)
            {
                val myactivity = Intent(context.applicationContext, AddProductActivity::class.java)
                myactivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.applicationContext.startActivity(myactivity)
            }
            if(position == 3)
            {
                val myactivity = Intent(context.applicationContext, ViewCategoryActivity::class.java)
                myactivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.applicationContext.startActivity(myactivity)
            }
            if (position == 4)
            {
                val myactivity = Intent(context.applicationContext, AboutUsActivity::class.java)
                myactivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.applicationContext.startActivity(myactivity)

            }


        }

    }
    override fun getItemCount(): Int
    {
        return list.size
    }

}
class MyViewDashboard(itemview: View) : RecyclerView.ViewHolder(itemview) {

    var image: CircularImageView = itemview.findViewById(R.id.circularImageView)
    var txt: TextView = itemview.findViewById(R.id.text)
}