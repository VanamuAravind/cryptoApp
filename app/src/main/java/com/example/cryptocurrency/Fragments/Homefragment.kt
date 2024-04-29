package com.example.cryptocurrency.Fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrency.Activities.convertor
import com.example.cryptocurrency.R
import com.example.cryptocurrency.allDataViewAdapter
import com.example.cryptocurrency.currency
import com.example.cryptocurrency.data
import com.example.cryptocurrency.myAdapter
import com.example.cryptocurrency.recentAmountAdapter
import com.example.cryptocurrency.recentCurrencyList
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.NonDisposableHandle.parent

class Homefragment(public var sharedPreferences: SharedPreferences,var newpref:SharedPreferences,var bottomNav:BottomNavigationView) : Fragment() {
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var currencyArrayList : ArrayList<currency>
    private lateinit var recentRecyclerView:RecyclerView
    private lateinit var recentCurrList:ArrayList<recentCurrencyList>
    private lateinit var userAmount:TextView
    private lateinit var profileImage:ImageView
    private lateinit var userName:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_homefragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileImage=view.findViewById(R.id.profileImage)
        profileImage.setOnClickListener{
            bottomNav.selectedItemId=R.id.profile
        }
        userName=view.findViewById(R.id.greetings)
        userAmount=view.findViewById(R.id.userAmount)
        fetUserDetailsFromSharedPreferences()

        userAmount.text="$"+getUserAmount()
        val imageId= intArrayOf(
            R.drawable.bitcoin,
            R.drawable.ethereum,
            R.drawable.trox,
            R.drawable.dogecoin,
            R.drawable.binance,
            R.drawable.solana,
            R.drawable.avalanche,
            R.drawable.polygon,
            R.drawable.maker,
            R.drawable.injective
        )
        val currencyName = arrayOf("Bitcoin","Ethereum","Trox","Dogecoin","Binance Coin","Solana","Avalanche","Polygon","Maker","Injective")
        val currencyAmount = arrayOf("$64,691.40","$3,098.38","$0.11","$0.15","$554.89","$139.09","$35.23","$0.68","$3,195.71","$28.72")
        val currencyCode = arrayOf("BTC","ETH","TRX","DOG","BC","SOL","AVL","PLY","MKR","INJ")

        currencyArrayList=ArrayList()
        for( i in currencyName.indices){
            val curr= currency(currencyName[i],currencyCode[i],imageId[i],currencyAmount[i])
            currencyArrayList.add(curr)
        }
        newRecyclerView=view.findViewById(R.id.my_horizontal_list_view)
        newRecyclerView.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        newRecyclerView.setHasFixedSize(true)
        newRecyclerView.adapter=myAdapter(currencyArrayList)

        addDataToRecentCurrency()
        recentRecyclerView=view.findViewById(R.id.recentListView)
        recentRecyclerView.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        recentRecyclerView.setHasFixedSize(true)
        recentRecyclerView.adapter=recentAmountAdapter(recentCurrList)
        val convertorBtn=view.findViewById<Button>(R.id.button)
        convertorBtn.setOnClickListener {
            val intent=Intent(context,convertor::class.java)
            startActivity(intent)
        }

        val viewMoreCurrencyYouHave=view.findViewById<TextView>(R.id.viewMorerecentGains)
        viewMoreCurrencyYouHave.setOnClickListener{
            bottomNav.selectedItemId=R.id.balance
        }
    }

    fun getData():ArrayList<data>{
        val jsonString=sharedPreferences.getString("data",null)
        return if(jsonString!=null)
            Gson().fromJson(jsonString,object : TypeToken<ArrayList<data>>(){}.type)
        else
            ArrayList()
    }
    fun getUserAmount():String{
        val data=getData()
        var total:Double=0.0
        for(i in data){
            total+=i.USDAmount
        }
        return String.format("%.2f",total)
    }

    fun addDataToRecentCurrency(){
        recentCurrList= ArrayList()
        val currdata=getData()
        for(i in currdata.indices){
            val curr=recentCurrencyList(currdata[i].currencyImage,currdata[i].cuurencyName,currdata[i].currencyAmount.toString(),"$"+currdata[i].USDAmount.toString())
            recentCurrList.add(curr)
        }
    }

    fun fetUserDetailsFromSharedPreferences(){
        val sharedPreferences= newpref
        val uriString=sharedPreferences.getString("image_uri",null)
        if (uriString==null)
            profileImage.setImageResource(R.drawable.profile)
        else{
            val uri= Uri.parse(uriString)
            Glide.with(requireView())
                .load(uri)
                .into(profileImage)
        }
        val username=sharedPreferences.getString("user_name",null)
        if(username==null){
            userName.text="Hello John Doe"
        }
        else{
            userName.text="Hello "+username
        }
    }
}