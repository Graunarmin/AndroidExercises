package com.example.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

const val EXTRA_QUESTION_LOCATION = "EXTRA_QUESTION_LOCATION"
const val EXTRA_QUESTION_CHEAT_INFO = "EXTRA_QUESTION_CHEAT_INFO"
private const val TAG = "QuizGame"

class QuizGame : AppCompatActivity()
{
    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button
    private lateinit var btnNext: Button
    private lateinit var tvQuestion: TextView
    private lateinit var playerName: String
    private lateinit var btnShowMap: Button
    private lateinit var btnCheat: Button

    private lateinit var questions: QuestionPool

    private val resultContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result: ActivityResult? ->
        if (result?.resultCode == Activity.RESULT_OK)
        {
            Log.i(TAG, "Result received")
        }
        else
        {
            Log.i(TAG, "Result not received")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_game)

        //Initialize Members
        btnTrue = findViewById(R.id.btnTrue)
        btnFalse = findViewById(R.id.btnFalse)
        btnNext = findViewById(R.id.btnNext)
        tvQuestion = findViewById(R.id.tvQuestion)
        btnShowMap = findViewById(R.id.btnShowOnMap)
        btnCheat = findViewById(R.id.btnCheat)

        playerName = intent.getStringExtra(EXTRA_USER_NAME).toString()

        Log.i(TAG, "User Name: $playerName")

        //Add Listeners to Buttons
        setOnClickListeners()

        // Create List of Questions
        questions = QuestionPool()

        //Write first Question to Screen
        tvQuestion.setText(questions.getCurrentQuestion())

    }

    private fun setOnClickListeners()
    {
        btnFalse.setOnClickListener{
            onAnswerButton(it, false)
        }

        btnTrue.setOnClickListener{
            onAnswerButton(it, true)
        }

        btnNext.setOnClickListener{
            showNextQuestion()
        }

        btnShowMap.setOnClickListener{
            onShowMapButton(it)
        }

        btnCheat.setOnClickListener{
            onCheatButton(it)
        }
    }

    private fun onAnswerButton(view: View, answer: Boolean)
    {
        checkAnswer(answer)
    }

    private fun onShowMapButton(view: View)
    {
        val location: String = "Germany"
        startNewActivity(EXTRA_QUESTION_LOCATION, location, ShowMap::class.java)
    }

    private fun onCheatButton(view: View)
    {
        startNewActivity(EXTRA_QUESTION_CHEAT_INFO, questions.getAnswer(), CheatActivity::class.java)
    }

    private fun checkAnswer(answer: Boolean)
    {
        Log.i(TAG, "Button '${answer}' was pressed")

        val toastText : String

        if(questions.answerIsCorrect(answer))
        {
            toastText = getString(R.string.correctAnswer)
        }
        else
        {
            toastText = getString(R.string.wrongAnswer)
        }

        //Show Toast with Check
        Toast(this).apply {
            duration = Toast.LENGTH_LONG
        }
        Toast.makeText(applicationContext, toastText, Toast.LENGTH_SHORT).show()
    }

    private fun showNextQuestion()
    {
        tvQuestion.setText(questions.getNextQuestion())
    }

    private fun showRandomQuestion()
    {
        tvQuestion.setText(questions.getRandomQuestion())
    }

    private fun startNewActivity(tag: String, message: String, cls: Class<*>?, requestCode: Int = 0)
    {
        val intent = Intent(this, cls).apply {
            putExtra(tag, message)
        }

        if (requestCode == 0)
        {
            startActivity(intent)
        }else
        {
            resultContract.launch(intent)
        }

    }
}