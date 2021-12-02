package com.example.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.btnStart)

        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        btnStart.setOnClickListener{
            onStartButtonClick(it)
        }
    }

    private fun onStartButtonClick(view: View){
        val intent = Intent(this, PlayerSetup::class.java)
        startActivity(intent)
    }
}
