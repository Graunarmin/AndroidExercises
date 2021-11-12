package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

const val EXTRA_MESSAGE = "com.example.tictactoe.playernames"

class PlayerSetup : AppCompatActivity() {

    private lateinit var etPlayerOne : EditText
    private lateinit var etPlayerTwo : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_setup)

        etPlayerOne = findViewById(R.id.etPlayerOne)
        etPlayerTwo = findViewById(R.id.etPlayerTwo)
    }

    fun onSubmitButtonClick(view: View){

        val playerOneName = etPlayerOne.text.toString()
        val playerTwoName = etPlayerTwo.text.toString()

        // Check that the strings are not empty!!

        val intent = Intent(this, GameDisplay::class.java).apply {
            putExtra(EXTRA_MESSAGE, arrayOf(playerOneName, playerTwoName))
        }
        startActivity(intent)
    }
}