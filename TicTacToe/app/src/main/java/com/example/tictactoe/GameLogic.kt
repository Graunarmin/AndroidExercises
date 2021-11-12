package com.example.tictactoe

class GameLogic (rows : Int, cols: Int) {

    var gameBoard = Array(rows){ IntArray(cols)}

    fun updateGameBoard(row : Int, col: Int) : Boolean
    {
        if(gameBoard[row-1][col-1] == 0)
        {
            //check which player clicked and update game board
            //https://www.youtube.com/watch?v=UQtSdNqtQPE
            return true
        }
        else
        {
            return false
        }
    }

}

