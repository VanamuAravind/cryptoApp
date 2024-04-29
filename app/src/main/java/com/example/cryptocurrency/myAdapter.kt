package com.example.cryptocurrency

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class myAdapter(private val arrayList:ArrayList<currency>) : RecyclerView.Adapter<myAdapter.MyViewHolder>(){
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val currencyName:TextView=itemView.findViewById(R.id.currencyName)
        val image:ImageView = itemView.findViewById(R.id.currencyImage)
        val code:TextView = itemView.findViewById(R.id.currenctCode)
        val amount:TextView = itemView.findViewById(R.id.amount)
        val card:View = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.card1,parent,false)
        return MyViewHolder(itemView)
    }
    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val allbgs= intArrayOf(R.drawable.card1_background2,R.drawable.card1_background,R.drawable.card1_background3,R.drawable.card1_background4,R.drawable.card1_background5,R.drawable.card1_background6)
        val randomBg=allbgs.indices.random()
        val currentItem=arrayList[position]
        holder.image.setImageResource(currentItem.image)
        holder.amount.text=currentItem.amount
        holder.code.text=currentItem.code
        holder.currencyName.text=currentItem.name
        holder.card.setBackgroundResource(allbgs[randomBg])
    }
}