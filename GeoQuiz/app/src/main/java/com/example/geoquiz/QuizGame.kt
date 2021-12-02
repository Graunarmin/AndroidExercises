package com.example.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.view.View

const val EXTRA_QUESTION_LOCATION = "EXTRA_QUESTION_LOCATION"
private const val TAG = "QuizGame"

class QuizGame : AppCompatActivity() {
    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button
    private lateinit var btnNext: Button
    private lateinit var tvQuestion: TextView
    private lateinit var playerName: String
    private lateinit var btnShowMap: Button

    private lateinit var questions: List<Question>
    private var questionIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_game)

        //Initialize Members
        btnTrue = findViewById(R.id.btnTrue)
        btnFalse = findViewById(R.id.btnFalse)
        btnNext = findViewById(R.id.btnNext)
        tvQuestion = findViewById(R.id.tvQuestion)
        btnShowMap = findViewById(R.id.btnShowOnMap)

        playerName = intent.getStringExtra(EXTRA_USER_NAME).toString()

        Log.i(TAG, "User Name: $playerName")

        //Add Listeners to Buttons
        setOnClickListeners()


        // Create List of Questions
        questions = createQuestions()

        //Write first Question to Screen
        tvQuestion.setText(getQuestionString())

    }

    private fun setOnClickListeners(){
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
    }

    private fun onShowMapButton(view: View){
        startNewIntent("Germany")
    }

    private fun onAnswerButton(view: View, answer: Boolean){
        checkAnswer(answer)
    }

    private fun checkAnswer(answer: Boolean){
        Log.i(TAG, "Button '${answer.toString()}' was pressed")

        val toastText : String

        if(getCurrentQuestion().answerCorrect(answer))
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
        Toast.makeText(applicationContext, "$toastText", Toast.LENGTH_SHORT).show()
    }

    private fun startNewIntent(message: String){
        val intent = Intent(this, ShowMap::class.java).apply {
            putExtra(EXTRA_QUESTION_LOCATION, message)
        }
        startActivity(intent)
    }

    // Question Kram, der ausgelagert werden sollte

    private fun getCurrentQuestion() : Question{
        return questions[questionIndex]
    }

    private fun getQuestionString(): Int{
        return questions[questionIndex].questionId
    }

    private fun showNextQuestion(){
        questionIndex = (questionIndex + 1) % questions.size
        tvQuestion.setText(getQuestionString())
    }

    private fun showRandomQuestion(){
        questionIndex = (questionIndex + (1..questions.size).random()) % questions.size
        tvQuestion.setText(getQuestionString())
    }

    private fun createQuestions(): List<Question>{
        return listOf(
            Question(R.string.q_Africa, true),
            Question(R.string.q_Alaska, true),
            Question(R.string.q_Antarctica, true),
            Question(R.string.q_MtChimborazo, true),
            Question(R.string.q_Russia, true)
        )
    }

}