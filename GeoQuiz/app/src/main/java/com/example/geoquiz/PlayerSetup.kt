package com.example.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

const val EXTRA_USER_NAME = "EXTRA_USER_NAME"
private const val TAG = "com.example.geoquiz.playersetup"

class PlayerSetup : AppCompatActivity() {

    private lateinit var etNameInput: EditText
    private lateinit var btnPlay: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_setup)

        etNameInput = findViewById(R.id.etNameInput)
        btnPlay = findViewById(R.id.btnPlay)

        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        btnPlay.setOnClickListener{
            onPlayButtonClick(it)
        }
    }

    private fun onPlayButtonClick(view: View)
    {
        if (etNameInput.text.toString() == "")
        {
            Toast.makeText(this, "Please enter your name.", Toast.LENGTH_SHORT).show()
        }else
        {
            startNewIntent(etNameInput.text.toString())
        }
    }

    private fun startNewIntent(message: String){
        val intent = Intent(this, QuizGame::class.java).apply {
            putExtra(EXTRA_USER_NAME, message)
        }
        startActivity(intent)
    }
}