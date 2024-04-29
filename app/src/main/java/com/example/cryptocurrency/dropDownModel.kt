package com.example.cryptocurrency

data class dropDownModel(public val image:Int,public val currencyName:String){
    override fun toString(): String {
        return currencyName
    }
}
