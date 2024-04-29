package com.example.cryptocurrency

import android.app.Activity
import android.app.LauncherActivity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.Fragments.AccountFragment
import com.example.cryptocurrency.Fragments.Homefragment
import com.example.cryptocurrency.Fragments.profileFragment
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.ActivityMainBinding
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var bottomNav:BottomNavigationView
    private var mypref="mypref"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomAppBar:BottomAppBar=BottomAppBar(this)
        val sharedPreferences=getSharedPreferences(mypref, Context.MODE_PRIVATE)
        val newpref=getSharedPreferences("user_details",Context.MODE_PRIVATE)
        bottomNav=findViewById(R.id.bottomNavigationView)

        replaceFragment(Homefragment(sharedPreferences,newpref,bottomNav))
        bottomNav.setOnItemSelectedListener{
            when(it.itemId){
                R.id.home -> replaceFragment(Homefragment(sharedPreferences,newpref,bottomNav))
                R.id.balance -> replaceFragment(AccountFragment(supportFragmentManager,this,sharedPreferences))
                R.id.profile -> replaceFragment(profileFragment(this))
                else->{
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout_container,fragment)
        fragmentTransaction.commit()
    }

}