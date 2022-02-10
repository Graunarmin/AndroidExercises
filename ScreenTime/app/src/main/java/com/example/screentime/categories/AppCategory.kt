package com.example.screentime.categories

import com.example.screentime.R

/**
 * Enum that provides all data for the 10 Android App-Categories.
 */
enum class AppCategory(val printableName: String, val categoryId: Int, val imageResource: Int, val color: Int)
{
    UNDEFINED("Undefined",-1, R.drawable.ic_undefined, R.color.blue_gray),
    GAME("Games", 0, R.drawable.ic_games, R.color.beniukon_bronze),
    AUDIO("Audio", 1, R.drawable.ic_audio, R.color.algal_fuel),
    VIDEO("Video", 2, R.drawable.ic_video, R.color.desire),
    IMAGE("Images", 3, R.drawable.ic_image, R.color.nyc_taxy),
    SOCIAL("Social Media", 4, R.drawable.ic_social, R.color.c64_ntsc),
    NEWS("News", 5, R.drawable.ic_news, R.color.turquoise_topaz),
    MAPS("Maps", 6, R.drawable.ic_map, R.color.high_blue),
    PRODUCTIVITY("Productivity", 7, R.drawable.ic_productivity, R.color.lighter_purple),
    ACCESSIBILITY("Accessibility", 8, R.drawable.ic_accessibility, R.color.twinkle_blue)
}