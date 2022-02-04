package com.example.screentime.items

import com.example.screentime.R

enum class AppCategory(val printableName: String, val index: Int, val imageResource: Int)
{
    UNDEFINED("Undefined",-1, R.drawable.ic_undefined),
    GAME("Games", 0, R.drawable.ic_games),
    AUDIO("Audio", 1, R.drawable.ic_audio),
    VIDEO("Video", 2, R.drawable.ic_video),
    IMAGE("Images", 3, R.drawable.ic_image),
    SOCIAL("Social Media", 4, R.drawable.ic_social),
    NEWS("News", 5, R.drawable.ic_news),
    MAPS("Maps", 6, R.drawable.ic_map),
    PRODUCTIVITY("Productivity", 7, R.drawable.ic_productivity),
    ACCESSIBILITY("Accessibility", 8, R.drawable.ic_accessibility)
}