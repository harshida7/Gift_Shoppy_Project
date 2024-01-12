package com.example.giftshoppyproject_admin.Adapter

import android.content.Context
import android.content.Intent
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.giftshopproject.Client.ApiClient
import com.example.giftshoppyproject_admin.Activity.Apiinterface
import com.example.giftshoppyproject_admin.Activity.UpdateCategoryActivity
import com.example.giftshoppyproject_admin.Activity.ViewCategoryActivity
import com.example.giftshoppyproject_admin.Activity.ViewProductActivity
import com.example.giftshoppyproject_admin.Model.CategoryAddModel
import com.example.giftshoppyproject_admin.R
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.io.path.fileVisitor



class CategroyViewAdapter(var context: Context,var list: List<CategoryAddModel>):RecyclerView.Adapter<MyViewCategory>()
{

        lateinit var apiinterface: Apiinterface


    val filteredlist = ArrayList<CategoryAddModel>()

    init {
        filteredlist.addAll(list)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewCategory
    {
        var  layoutInflater= LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.design_category_view,parent,false)
        return MyViewCategory(view,list,context)

    }

    override fun getItemCount(): Int {
        return filteredlist.size
    }

    override fun onBindViewHolder(holder: MyViewCategory , position: Int)
    {

        holder.textView.setText(list.get(position).category_name)
        Picasso.with(context).load(list.get(position).category_img).placeholder(R.mipmap.ic_launcher).into(holder.imageview)

        holder.itemView.setOnClickListener {

            var i = Intent(context, ViewProductActivity::class.java)
            i.putExtra("mypos",list.get(position).id)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)

        }

    }


}
class MyViewCategory(Itemview: View, var list: List<CategoryAddModel>,var context: Context):RecyclerView.ViewHolder(Itemview), View.OnCreateContextMenuListener,
    MenuItem.OnMenuItemClickListener {

    lateinit var apiinterface: Apiinterface

    var textView: TextView = Itemview.findViewById(R.id.text)
    var imageview: CircularImageView = Itemview.findViewById(R.id.circularImageView)

        init {

                itemView.setOnCreateContextMenuListener(this)
        }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        val Edit: MenuItem =  menu!!.add(Menu.NONE, 1, Menu.NONE, "Update");
        val Delete: MenuItem = menu!!.add(Menu.NONE, 2, Menu.NONE, "Delete")

        Edit.setOnMenuItemClickListener(this);
        Delete.setOnMenuItemClickListener(this);
    }


    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> {
                val i = Intent(itemView.context.applicationContext, UpdateCategoryActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.putExtra("id",list.get(position).id)
                i.putExtra("cname",list.get(position).category_name)
                i.putExtra("cimg",list.get(position).category_img)
                itemView.context.applicationContext.startActivity(i)

                Toast.makeText(itemView.context, "Update", Toast.LENGTH_SHORT).show()
            }
            2 -> {

                apiinterface = ApiClient.getapiclient().create(Apiinterface::class.java)

                var call: Call<Void> = apiinterface.deletecategory(list.get(position).id)
                call!!.enqueue(object : Callback<Void>
                {
                    override fun onResponse(call: Call<Void>, response: Response<Void>)
                    {
                        Toast.makeText(context,"Product Deleted",Toast.LENGTH_LONG).show()

                        var i = Intent(context,ViewCategoryActivity::class.java)
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        itemView.context.applicationContext.startActivity(i)

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable)
                    {
                        Toast.makeText(context,"Error",Toast.LENGTH_LONG).show()
                    }


                })

            }
        }
     return true
    }
}

