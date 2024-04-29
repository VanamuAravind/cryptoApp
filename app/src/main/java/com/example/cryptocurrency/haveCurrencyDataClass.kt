package com.example.cryptocurrency

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.HashMap

data class data(val cuurencyName:String,val currencyImage:Int,val currencyAmount:Double,val USDAmount:Double)

class haveCurrencyDataClass(var sharedPreferences: SharedPreferences) {
    private var currencies:ArrayList<data>
    private lateinit var currencyValues : HashMap<String,Double>
    init {
        addDataToHashMap()
        currencies= ArrayList()
        currencies= getDataFromSharedPreferences()
    }
    fun addData(currency:String,image:Int,amount:Double){
        val newData=data(currency,image,amount,currencyValues[currency]!!*amount)
        currencies.add(newData)
    }
    fun getData():ArrayList<data>{
        return currencies
    }
    fun getDataFromSharedPreferences():ArrayList<data>{
        val jsonString=sharedPreferences.getString("data",null)
        return if(jsonString!=null)
            Gson().fromJson(jsonString,object : TypeToken<ArrayList<data>>(){}.type)
        else
            ArrayList()
    }
    fun deleteData(position:Int){
        if(currencies.size>0) {
            currencies.removeAt(position)
            val prefEditor=sharedPreferences.edit()
            val jsonString=Gson().toJson(getData())
            prefEditor.putString("data",jsonString).apply()
        }
    }
    fun addDataToHashMap(){
        currencyValues= HashMap()
        currencyValues["Bitcoin"]=64691.40
        currencyValues["Ethereum"]=3098.38
        currencyValues["Trox"]=0.11
        currencyValues["Dogecoin"]=0.15
        currencyValues["Binance Coin"]=554.89
        currencyValues["Solana"]=139.09
        currencyValues["Avalanche"]=35.23
        currencyValues["Polygon"]=0.68
        currencyValues["Maker"]=3195.71
        currencyValues["Injective"]=28.72
    }
}