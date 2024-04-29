package com.example.cryptocurrency

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class allDataViewAdapter(private val arrayList:ArrayList<data>,var dataobj:haveCurrencyDataClass,var sharedPreferences: SharedPreferences) : RecyclerView.Adapter<allDataViewAdapter.MyHolder>(){
    class MyHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val currency:TextView=itemView.findViewById(R.id.currency)
        val image:ImageView=itemView.findViewById(R.id.currencyimage)
        val currencyAmount:TextView=itemView.findViewById(R.id.currencyAmount)
        val usdAmount:TextView=itemView.findViewById(R.id.usdamount)
        val deleteButton:ImageButton=itemView.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.currency_custom_view,parent,false)
        return MyHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currItem=arrayList[position]
        holder.currency.text=currItem.cuurencyName
        holder.image.setImageResource(currItem.currencyImage)
        holder.currencyAmount.text=currItem.currencyAmount.toString()
        holder.usdAmount.text="$"+ String.format("%.2f",currItem.USDAmount)
        holder.deleteButton.setOnClickListener{
            dataobj.deleteData(position)
            this.notifyDataSetChanged()
        }
    }
}