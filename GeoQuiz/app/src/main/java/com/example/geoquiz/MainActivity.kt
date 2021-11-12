package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.util.Log
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button
    private lateinit var btnNext: Button
    private lateinit var tvQuestion: TextView

    private lateinit var questions: List<Question>
    private var questionIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize Members
        btnTrue = findViewById(R.id.btnTrue)
        btnFalse = findViewById(R.id.btnFalse)
        btnNext = findViewById(R.id.btnNext)
        tvQuestion = findViewById(R.id.tvQuestion)

        //Add Listeners to Buttons
        btnFalse.setOnClickListener{
            checkAnswer(false)
            showNextQuestion()
        }

        btnTrue.setOnClickListener{
            checkAnswer(true)
            showNextQuestion()
        }

        btnNext.setOnClickListener{
            showNextQuestion()
        }

        // Create List of Questions
        questions = createQuestions()

        //Write first Question to Screen
        tvQuestion.setText(getQuestionString())


    }

    private fun checkAnswer(answer: Boolean){
        Log.i(TAG, "Button '${answer.toString()}' was pressed")

        var toastText : String

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
