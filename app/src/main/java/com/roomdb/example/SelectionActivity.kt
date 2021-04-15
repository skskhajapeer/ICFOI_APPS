package com.roomdb.example

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.roomdb.example.utils.AppPreferences

class SelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        AppPreferences.isLogin = true

    }

    fun floraClick(view : View){

        val intent = Intent(this,AddingData::class.java)
        startActivity(intent)
    }
    fun faunaClick(view : View){

        val intent = Intent(this,FaunaActivity::class.java)
        startActivity(intent)
    }

}