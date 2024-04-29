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
import android.view.ViewParent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
class profileFragment(var parent: Context) : Fragment() {
    private lateinit var profileImage:ImageView
    private lateinit var imageURI:String
    private lateinit var userName:EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileImage=requireView().findViewById(R.id.profile_image)
        userName=view.findViewById(R.id.username)
        fetUserDetailsFromSharedPreferences()

        val mediaPicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){uri->
            if (uri!=null) {
                Glide.with( requireView())
                    .load(uri)
                    .into(profileImage)
                imageURI=uri.toString()
            }
        }
        profileImage.setOnClickListener{
            mediaPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        val savebtn=view.findViewById<Button>(R.id.savebtn)
        savebtn.setOnClickListener{
            val sharedPreferences=parent.getSharedPreferences("user_details",Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()
            if (imageURI!=null)
                editor.putString("image_uri",imageURI)
            if (userName.text.toString()!=null)
                editor.putString("user_name",userName.text.toString())
            editor.apply()

            Toast.makeText(context,"saved successfully",Toast.LENGTH_SHORT).show()
        }

    }

    fun fetUserDetailsFromSharedPreferences(){
        val sharedPreferences=parent.getSharedPreferences("user_details",Context.MODE_PRIVATE)
        val uriString=sharedPreferences.getString("image_uri",null)
        if (uriString==null)
            profileImage.setImageResource(R.drawable.profile)
        else{
            val uri=Uri.parse(uriString)
            imageURI=uri.toString()
            Glide.with(requireView())
                .load(uri)
                .into(profileImage)
        }
        val username=sharedPreferences.getString("user_name",null)
        if(username==null){
            userName.setText("John Doe")
        }
        else{
            userName.setText(username)
        }
    }


}