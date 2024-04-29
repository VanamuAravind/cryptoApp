package com.example.cryptocurrency.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.MainActivity
import com.example.cryptocurrency.R
import com.example.cryptocurrency.dropDownAdapter
import com.example.cryptocurrency.dropDownModel
import kotlin.properties.Delegates

class convertor : AppCompatActivity() {
    private lateinit var dropDownData:ArrayList<dropDownModel>
    private lateinit var fromCurrency : String
    private lateinit var toCurrency:String
    private var fromAmount by Delegates.notNull<Double>()
    private var toAmount by Delegates.notNull<Double>()
    private lateinit var currencyValues : HashMap<String,Double>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convertor)

        addDataToSpinnerDropdown()
        val fromSpinner = findViewById<Spinner>(R.id.fromCurrencySpinner)
        val toSpinner = findViewById<Spinner>(R.id.toCurrencySpinner)
        val dropDownAdapter=dropDownAdapter(this,dropDownData)
        fromSpinner.adapter=dropDownAdapter
        toSpinner.adapter=dropDownAdapter

        fromAmount=0.00
        toAmount=0.00
        addDataToHashMap()

        fromSpinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem=parent!!.getItemAtPosition(position)
                val s=selectedItem.toString()
                fromCurrency=selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        toSpinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem=parent!!.getItemAtPosition(position)
                toCurrency=selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        val convertBtn=findViewById<Button>(R.id.convertBtn)
        convertBtn.setOnClickListener {
            val fromInput=findViewById<EditText>(R.id.editTextText)
            if (fromInput.text.toString()!="") {
                val fromAmount = fromInput.text.toString().toDouble()
                val toAmount = convert(fromAmount)
                val toInput = findViewById<EditText>(R.id.editTextText1)
                toInput.setText(String.format("%.3f", toAmount).toString())
            }
            else{
                Toast.makeText(this,"Please give some value",Toast.LENGTH_SHORT).show()
            }
        }


        val backBtn=findViewById<ImageView>(R.id.back_icon)
        backBtn.setOnClickListener{
            finish()
        }




    }
    fun addDataToSpinnerDropdown(){
        dropDownData= ArrayList()
        val image= intArrayOf(R.drawable.bitcoin,R.drawable.ethereum,R.drawable.trox,R.drawable.avalanche,R.drawable.dogecoin,R.drawable.binance,R.drawable.solana,R.drawable.polygon,R.drawable.maker,R.drawable.injective)
        val currencies=arrayOf("Bitcoin","Ethereum","Trox","Avalanche","Dogecoin","Binance Coin","Solana","Polygon","Maker","Injective")
        for(i in image.indices){
            val curr=dropDownModel(image[i],currencies[i])
            dropDownData.add(curr)
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

    fun convert(fromAmount: Double): Double {
        if (fromCurrency != null && toCurrency != null) {
            val result = fromAmount * currencyValues[fromCurrency]!! / currencyValues[toCurrency]!!
            return result
        } else {
            // Handle the case where currencies are not selected (e.g., show a toast message)
            Toast.makeText(this, "Please select both currencies!", Toast.LENGTH_SHORT).show()
            return 0.0 // Or any default value you prefer
        }
    }


}