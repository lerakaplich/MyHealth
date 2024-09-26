package com.kaplich.myhealth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kaplich.myhealth.pages.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_medicine)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}