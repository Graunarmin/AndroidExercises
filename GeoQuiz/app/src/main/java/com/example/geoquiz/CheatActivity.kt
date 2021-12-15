package com.example.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible

private const val TAG ="CheatActivity"

class CheatActivity : AppCompatActivity() {

    private lateinit var btnShowAnswer: Button
    private lateinit var btnPhoneAFriend: Button
    private lateinit var btnNoCheat: Button
    private lateinit var tvCheatWarning: TextView
    private lateinit var tvCheatAnswer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cheat_activity)

        btnShowAnswer = findViewById(R.id.btnShowAnswer)
        btnPhoneAFriend = findViewById(R.id.btnPhoneAFriend)
        btnNoCheat = findViewById(R.id.btnNoCheat)
        tvCheatWarning = findViewById(R.id.tvCheatWarning)
        tvCheatAnswer = findViewById(R.id.tvCheatAnswer)
        tvCheatAnswer.isVisible = false

        setOnClickListeners()

    }

    private fun setOnClickListeners()
    {
        btnShowAnswer.setOnClickListener{
            onShowAnswerButton(it)
        }

        btnPhoneAFriend.setOnClickListener{
            onPhoneAFriendButton(it)
        }

        btnNoCheat.setOnClickListener{
            onNoCheatButton(it)
        }
    }

    private fun onShowAnswerButton(view: View)
    {
        // Show the answer to the question
        tvCheatAnswer.isVisible = true
        tvCheatAnswer.text = intent.getStringExtra(EXTRA_QUESTION_CHEAT_INFO)

        // TODO: Then return to the question
    }

    private fun onPhoneAFriendButton(view: View)
    {
        // TODO: Start new Activity to open the contacts-list or phone app
    }

    private fun onNoCheatButton(view: View)
    {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onBackPressed()
    {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

}