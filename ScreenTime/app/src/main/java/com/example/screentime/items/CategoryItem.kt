package com.example.screentime.items

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.screentime.ScreenTimeApp
import com.example.screentime.categories.AppCategory
import com.example.screentime.utils.formatUsageTime

/**
 * An Instance of [CategoryItem] represents one of the categories from the usage statistics.
 *
 * For each new category that is found in the app list a new instance if [CategoryItem] is created
 */

private const val TAG = "com.example.screentime.items.categoryitem"
class CategoryItem(categoryIndex: Int, var context: Context) : Item
{
    override val categoryId: Int = categoryIndex
    override val category: AppCategory
        get() = AppCategory.values()[categoryId + 1]

    override val itemName = category.printableName
    override val packageName: String = "none"
    override val icon = categoryIcon()
    override var limit: Int = -1

    val containedApps: ArrayList<AppItem> = getCategoryApps()
    override var wasUsed: Boolean = false
    override var useTime : Long = updateUseTime(0)
    override var readableUseTime: String = formatUsageTime(useTime)


    override fun updateUseTime(time: Long) : Long
    {
        var utime : Long = 0
        if(containedApps != null)
        {
            containedApps.forEach { app -> utime += app.useTime }
        }
        useTime = utime
        readableUseTime = formatUsageTime(useTime)
        wasUsed = useTime > 0
        return utime
    }

    private fun categoryIcon() : Drawable?
    {
        //ToDo: Find out why no Icon is shown
        return ContextCompat.getDrawable(context, category.imageResource)
    }

    private fun getCategoryApps() : ArrayList<AppItem>
    {
        return ScreenTimeApp.appInstance.appList.getAllItemsOfCategory(categoryId) as ArrayList<AppItem>
    }
}