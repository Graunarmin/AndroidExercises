package com.example.screentime.items

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

data class CategoryItem (var category: AppCategory, var apps: ArrayList<AppItem>, var context: Context) :
        Item
{
    override val name = category.printableName
    override val icon = categoryIcon()
    override var useTime : Long = computeUseTime()

    private fun computeUseTime() : Long
    {
        var time : Long = 0
        apps.forEach { app -> time += app.useTime }

        return time
    }

    private fun categoryIcon() : Drawable?
    {
        return ContextCompat.getDrawable(context, category.imageResource)
    }
}