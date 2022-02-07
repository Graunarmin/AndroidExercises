package com.example.screentime.categories

import android.app.usage.UsageStats
import android.content.Context
import com.example.screentime.ScreenTimeApp
import com.example.screentime.timeitems.AppTimeItem
import java.util.*
import kotlin.collections.ArrayList

class CategoriesList(usageStats: List<UsageStats>, context: Context)
{
    var allCategories : EnumMap<AppCategory, ArrayList<String>> = EnumMap(AppCategory::class.java)

    init
    {
        for (entry in usageStats)
        {
            ScreenTimeApp.appInstance.appList.add(AppTimeItem(entry, context))
        }

        for(category in AppCategory.values())
        {
            val apps = ScreenTimeApp.appInstance.appList.filter { app -> app.category == category.index }
            val appNames: ArrayList<String> = ArrayList()

            for (app in apps)
            {
                appNames.add(app.name)
            }
            appNames.sort()
            allCategories[category] = appNames
        }
    }

    private val categoriesOrder = ArrayList(allCategories.keys)

    val size : Int
        get() = allCategories.size

    fun appCountAt(index: Int) : Int
    {
        return allCategories[categoriesOrder[index]]!!.size
    }

    fun categoryName(index: Int) : String
    {
        return categoriesOrder[index].printableName
    }

    fun getAppName(catIndex: Int, appIndex: Int) : String
    {
        return allCategories[categoriesOrder[catIndex]]!![appIndex]
    }
}