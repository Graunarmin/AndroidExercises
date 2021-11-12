package com.example.tictactoe

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.ceil
import kotlin.math.min

class TicTacToeBoard(context : Context, attrs: AttributeSet?) : View(context, attrs) {

    val a = context.obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard)

    var reduction = 0f

    var boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0)
    var xColor = a.getInteger(R.styleable.TicTacToeBoard_XColor, 0)
    var oColor = a.getInteger(R.styleable.TicTacToeBoard_OColor, 0)
    var winningColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor, 0)


    var game : GameLogic = GameLogic(3, 3)
    var paint : Paint = Paint()
    var dimension : Int = 0
    var cellSize : Int = width / 3

    @Override
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        dimension = min(measuredWidth, measuredHeight)
        cellSize = dimension / 3

        // set to square
        setMeasuredDimension(dimension, dimension)

        reduction = (cellSize * 0.2f)
    }

    @Override
    override fun onDraw(canvas: Canvas) {
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true

        drawGameBoard(canvas)
        drawMarkers(canvas)
    }

    private fun drawMarkers(canvas: Canvas)
    {
        //Scan Board to determine whether to place an X or an O


    }

    @Override
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var xPos = event.x
        var yPos = event.y

        var action = event.action
        if(action == MotionEvent.ACTION_DOWN)
        {
            var row = ceil(y/cellSize)
            var col = ceil(x/cellSize)

            invalidate()
            return true
        }
        return false
    }

    private fun drawGameBoard(canvas: Canvas) {
        paint.color = boardColor
        paint.strokeWidth = 16f

        for(c in 1..2)
        {
            canvas.drawLine((cellSize * c).toFloat(), 0f, (cellSize * c).toFloat(), (canvas.width).toFloat(), paint)

            for(r in 1..2)
            {
                canvas.drawLine(0f, (cellSize * r).toFloat(), (canvas.width).toFloat(), (cellSize * r).toFloat(), paint)
            }
        }
    }

    private fun drawX(canvas : Canvas, row: Float, col: Float){
        paint.color = xColor
        canvas.drawLine((col + 1) * cellSize - reduction,
            row * cellSize + reduction,
            col * cellSize + reduction,
            (row + 1) * cellSize - reduction,
            paint)

        canvas.drawLine(col * cellSize + reduction,
            row * cellSize + reduction,
            (col + 1) * cellSize - reduction,
            (row + 1) * cellSize - reduction,
            paint)
    }

    private fun drawO(canvas : Canvas, row: Float, col: Float){
        paint.color = oColor
        canvas.drawOval(col * cellSize + reduction,
            row * cellSize + reduction,
            (col * cellSize + cellSize) - reduction,
            (row * cellSize + cellSize) - reduction,
            paint)
    }
}