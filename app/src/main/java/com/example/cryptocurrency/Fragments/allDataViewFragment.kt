package com.example.cryptocurrency.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.R
import com.example.cryptocurrency.allDataViewAdapter
import com.example.cryptocurrency.haveCurrencyDataClass

class allDataViewFragment(private var allDataViewAdapter:allDataViewAdapter,private var dataobj:haveCurrencyDataClass) : Fragment() {
    private lateinit var recyclerView:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_data_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=view.findViewById(R.id.allrecyclerview)
        recyclerView.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter=allDataViewAdapter

    }
    fun getAdapter():allDataViewAdapter{
        return allDataViewAdapter
    }

}