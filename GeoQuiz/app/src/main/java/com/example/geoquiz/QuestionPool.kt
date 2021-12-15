package com.example.geoquiz

class QuestionPool {

    private var questionPool: List<Question> = listOf(
        Question(R.string.q_Africa, true),
        Question(R.string.q_Alaska, true),
        Question(R.string.q_Antarctica, true),
        Question(R.string.q_MtChimborazo, true),
        Question(R.string.q_Russia, true)
    )

    private var index = 0

    fun answerIsCorrect(answer: Boolean): Boolean{
        return questionPool[index].answer == answer
    }

    fun getCurrentQuestion(): Int{
        return questionPool[index].questionId
    }

    fun getNextQuestion(): Int{
        index = (index + 1) % questionPool.size
        return getCurrentQuestion()
    }

    fun getRandomQuestion(): Int{
        index = (index + (1..questionPool.size).random()) % questionPool.size
        return getCurrentQuestion()
    }

    fun getAnswer(): String{
        return questionPool[index].answer.toString()
    }
}