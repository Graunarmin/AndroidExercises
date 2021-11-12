package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class GameDisplay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_display)
    }

    fun onPlayAgainButton(view : View)
    {
        //creta Game Board
    }

    fun onHomeButton(view : View)
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}