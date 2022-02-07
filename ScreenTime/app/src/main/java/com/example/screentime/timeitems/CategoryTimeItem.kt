package com.example.screentime.timeitems

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.screentime.categories.AppCategory

data class CategoryTimeItem (var category: AppCategory, var apps: ArrayList<AppTimeItem>, var context: Context) :
        TimeItem
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
        //ToDo: Find out why no Icon is shown
        return ContextCompat.getDrawable(context, category.imageResource)
    }
}