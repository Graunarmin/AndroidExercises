package com.example.screentime

import android.graphics.drawable.Drawable

data class AppCategory(var appList: ArrayList<App>)
{
    var categoryName = categoryName()
    var categoryTime = categoryTime()
    var categoryIcon = categoryIcon()

    private fun categoryName() : String
    {
        return ""
    }

    private fun categoryTime() : String
    {
        return ""
    }

    private fun categoryIcon() : Int
    {
        return 0
    }
}

enum class Kategorie(val index: Int, val imageResource: Int)
{
    UNDEFINED(-1, R.drawable.games_ic),
    GAME(0, R.drawable.games_ic),
    AUDIO(1, R.drawable.audio_ic),
    VIDEO(2, R.drawable.video_ic),
    IMAGE(3, R.drawable.image_ic),
    SOCIAL(4, R.drawable.social_ic),
    NEWS(5, R.drawable.news_ic),
    MAPS(6, R.drawable.games_ic),
    PRODUCTIVITY(7, R.drawable.games_ic),
    ACCESSIBILITY(8, R.drawable.games_ic)
}