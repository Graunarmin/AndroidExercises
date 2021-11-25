package com.example.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class PlayerSetup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_setup)
    }

    fun onPlayButtonClick(view: View){
        val intent = Intent(this, QuizGame::class.java)
        startActivity(intent)
    }
}