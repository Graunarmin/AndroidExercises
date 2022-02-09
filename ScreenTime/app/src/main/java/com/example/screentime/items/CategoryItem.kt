package com.example.screentime.items

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.screentime.ScreenTimeApp
import com.example.screentime.categories.AppCategory
import com.example.screentime.utils.formatUsageTime

private const val TAG = "<-!-DEBUG-!-> com.example.screentime.items.categoryitem"
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
    override var useTime : Long = updateUseTime(0)
    override var readableUseTime: String = formatUsageTime(useTime)

    override fun updateUseTime(time: Long) : Long
    {
        var utime : Long = 0
        if(containedApps != null)
        {
            containedApps.forEach { app -> utime += app.useTime
            Log.d(TAG, "Usetime for ${app.itemName} in category $itemName is ${app.useTime}")}
        }

        Log.d(TAG, "Total Use Time for $itemName is $utime")
        readableUseTime = formatUsageTime(useTime)
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