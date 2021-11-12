package com.example.geoquiz

class Question (var questionId: Int, var answer: Boolean) {

    fun answerCorrect(input: Boolean) : Boolean{
        return input == answer
    }

}