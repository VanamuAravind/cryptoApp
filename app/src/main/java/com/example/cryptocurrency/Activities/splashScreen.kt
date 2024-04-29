package com.example.cryptocurrency.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.cryptocurrency.MainActivity
import com.example.cryptocurrency.R

class splashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val img=findViewById<ImageView>(R.id.app_icon)
        img.alpha=0f
        img.animate().setDuration(1500).alpha(1f).withEndAction{
            val i=Intent(this,MainActivity::class.java)
            startActivity(i)
        }
    }
}