package com.example.giftshoppy_project.Adapter

import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.giftshoppy_project.Model.CartModel
import com.example.giftshoppy_project.Activity.PaymentActivity
import com.example.giftshoppy_project.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso

class CartAddAdapter (var context: Context, var list: List<CartModel>):RecyclerView.Adapter<MyViewCart>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewCart
    {
        var  layoutInflater= LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.design_cart,parent,false)
        return MyViewCart(view)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewCart , position: Int)
    {

       /* holder.btnPlus.setBackgroundColor(Color.TRANSPARENT)
        holder.btnMinus.setBackgroundColor(Color.TRANSPARENT)
*/
        holder.textView.setText(list.get(position).product_name)
        holder.textView2.setText(list.get(position).product_price.toString())
        var c_id = list.get(position).c_id
        Picasso.with(context).load(list.get(position).product_image).into(holder.imageview)

        holder.btnPay.setOnClickListener {

            var i = Intent(context, PaymentActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.putExtra("id", list.get(position).id)
            i.putExtra("name", list.get(position).product_name)
            i.putExtra("description", list.get(position).product_des)
            i.putExtra("price", list.get(position).product_price)
            i.putExtra("image", list.get(position).product_image)
            context.startActivity(i)

        }

        if(isNightMode(context))
        {
            holder.cardCounter.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            holder.cardCounter.setStrokeColor(Color.parseColor("#990011"))
            holder.cardAdd.setCardBackgroundColor(Color.parseColor("#990011"))
            holder.cardRemove.setCardBackgroundColor(Color.parseColor("#990011"))

        }
        else
        {
            holder.cardCounter.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            holder.cardCounter.setStrokeColor(Color.parseColor("#990011"))
            holder.cardAdd.setCardBackgroundColor(Color.parseColor("#990011"))
            holder.cardRemove.setCardBackgroundColor(Color.parseColor("#990011"))

        }
    }
}
class MyViewCart(Itemview: View): RecyclerView.ViewHolder(Itemview)
{
    var textView: TextView = Itemview.findViewById(R.id.txtProductName)
    var textView2: TextView = Itemview.findViewById(R.id.txtProductPrice)
    var imageview: ImageView = Itemview.findViewById(R.id.imgProduct)
    var btnPay: Button = Itemview.findViewById(R.id.btnpayment)
    var btnDelete: Button = Itemview.findViewById(R.id.btnRemove)
    var cardCounter:MaterialCardView = Itemview.findViewById(R.id.cardCounterMain)
    var cardAdd:CardView = Itemview.findViewById(R.id.cardAddItem)
    var cardRemove:CardView = Itemview.findViewById(R.id.cardRemoveItem)



}

    fun isNightMode(context: Context): Boolean {
    val nightModeFlags =context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
}
