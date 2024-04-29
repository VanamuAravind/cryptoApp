package com.example.cryptocurrency

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class dropDownAdapter(context:Context,arrayList:ArrayList<dropDownModel>):ArrayAdapter<dropDownModel>(context,0,arrayList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initview(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initview(position, convertView, parent)
    }
    private fun initview(position: Int, convertView: View?, parent: ViewGroup):View{
        val item=getItem(position)
        val view=LayoutInflater.from(context).inflate(R.layout.custom_dropdown_item,parent,false)
        val image:ImageView=view.findViewById(R.id.image)
        val currency:TextView=view.findViewById(R.id.currency)
        image.setImageResource(item!!.image)
        currency.text=item!!.currencyName
        return view
    }
}