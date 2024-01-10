package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(var items: List<Item>, var context: Context, var login:String): RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val stock = view.findViewById<TextView>(R.id.item_stock)
        val kolvo = view.findViewById<TextView>(R.id.item_kolvo)
        val price = view.findViewById<TextView>(R.id.item_price)
        val btn = view.findViewById<Button>(R.id.list_toStat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.stock.text = items[position].name
        holder.kolvo.text = items[position].kolvo.toString() + " шт."
        holder.price.text = items[position].price.toString() + " ₽."

        holder.btn.setOnClickListener {
            val intent = Intent(context, Statistik::class.java)
            intent.putExtra("stock", items[position].name)
            intent.putExtra("login", login)
            context.startActivity(intent)
        }
    }

}