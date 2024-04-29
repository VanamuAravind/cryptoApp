package com.example.cryptocurrency.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptocurrency.R
import com.example.cryptocurrency.allDataViewAdapter
import com.example.cryptocurrency.dropDownAdapter
import com.example.cryptocurrency.dropDownModel
import com.example.cryptocurrency.haveCurrencyDataClass
import com.example.cryptocurrency.viewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import kotlin.properties.Delegates

class AccountFragment(private val supportManager:FragmentManager,private val context:Context,private var sharedPreferences: SharedPreferences) : Fragment() {
    private lateinit var tabLayout:TabLayout
    private lateinit var viewPager2:ViewPager2
    private lateinit var pagerAdapter: viewPagerAdapter
    private lateinit var dropDownData:ArrayList<dropDownModel>
    private lateinit var currencyValues : HashMap<String,Double>
    private lateinit var selectedCurrency:String
    private var selectedCurrencyImage by Delegates.notNull<Int>()
    private lateinit var currencyAmount : EditText
    private lateinit var dataObj:haveCurrencyDataClass
    private lateinit var alldataviewfragment_adapter:allDataViewAdapter

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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataObj=haveCurrencyDataClass(sharedPreferences)

        tabLayout=view.findViewById(R.id.tabLayout)
        viewPager2=view.findViewById(R.id.viewPager2)


        alldataviewfragment_adapter = allDataViewAdapter(dataObj.getData(),dataObj,sharedPreferences)
        val alldataviewfragment= allDataViewFragment(alldataviewfragment_adapter,dataObj)


        val fragments= listOf<Fragment>(alldataviewfragment)
        pagerAdapter= viewPagerAdapter(fragments,supportManager,lifecycle)
        viewPager2.adapter=pagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = when (position) {
                0 -> "Available Currencies"
                else -> null
            }
        }.attach()

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        val plusButton=view.findViewById<ImageButton>(R.id.plusButton)
        plusButton.setOnClickListener{
            showCustomDialogBox()
        }




    }

    //custom dialog box
    private fun showCustomDialogBox(){
        val customDialogBox=AlertDialog.Builder(context)
            .setView(inflateDialogBox())
            .setPositiveButton("Save"){ dialog,which->
                dataObj.addData(selectedCurrency,selectedCurrencyImage,currencyAmount.text.toString().toDouble())
                alldataviewfragment_adapter.notifyDataSetChanged()

                val prefEditor=sharedPreferences.edit()
                val jsonString=Gson().toJson(dataObj.getData())
                prefEditor.putString("data",jsonString).apply()
                dialog.dismiss()
            }
            .setNegativeButton("close"){dialog,which->
                dialog.dismiss()
            }
        val dialog = customDialogBox.create()
        dialog.show()
    }
    private fun inflateDialogBox():View{
        val inflater=LayoutInflater.from(context)
        val view=inflater.inflate(R.layout.dialog_box,null)
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        addDataToSpinnerDropdown()
        val dropDownAdapter= dropDownAdapter(context,dropDownData)
        spinner.adapter=dropDownAdapter
        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCurrency=dropDownData[position].currencyName.toString()
                selectedCurrencyImage=dropDownData[position].image
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        currencyAmount=view.findViewById(R.id.currencyEntered)

        return view
    }

    fun addDataToSpinnerDropdown(){
        dropDownData= ArrayList()
        val image= intArrayOf(R.drawable.bitcoin,R.drawable.ethereum,R.drawable.trox,R.drawable.avalanche,R.drawable.dogecoin,R.drawable.binance,R.drawable.solana,R.drawable.polygon,R.drawable.maker,R.drawable.injective)
        val currencies=arrayOf("Bitcoin","Ethereum","Trox","Avalanche","Dogecoin","Binance Coin","Solana","Polygon","Maker","Injective")
        for(i in image.indices){
            val curr= dropDownModel(image[i],currencies[i])
            dropDownData.add(curr)
        }
    }
}