package com.example.cryptocurrency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class recentAmountAdapter(private val arrayList:ArrayList<recentCurrencyList>):RecyclerView.Adapter<recentAmountAdapter.MyViewHolder>() {
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val image:ImageView = itemView.findViewById(R.id.image)
        val currencyName:TextView=itemView.findViewById(R.id.currency)
        val currencyCode:TextView=itemView.findViewById(R.id.cuurencyCode)
        val amount:TextView=itemView.findViewById(R.id.amount)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): recentAmountAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.recent_gain_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: recentAmountAdapter.MyViewHolder, position: Int) {
        val currentItem=arrayList[position]
        holder.image.setImageResource(currentItem.image)
        holder.currencyName.text=currentItem.currency
        holder.currencyCode.text=currentItem.code
        holder.amount.text=currentItem.amount
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


}