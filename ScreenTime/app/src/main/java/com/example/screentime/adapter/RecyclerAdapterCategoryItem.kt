package com.example.screentime.adapter

import android.content.Context
import com.example.screentime.categories.AppCategory
import com.example.screentime.ScreenTimeApp
import com.example.screentime.timeitems.AppTimeItem
import com.example.screentime.timeitems.CategoryTimeItem
import com.example.screentime.timeitems.TimeItem

private const val TAG = "<DEBUG> com.example.screentime.appcategories.RecyclerAdapterCategories"
class RecyclerAdapterCategoryItem(itemClickListener: OnTimeItemClickListener) : RecyclerAdapterItem(itemClickListener)
{
    override var itemList: ArrayList<TimeItem> = ScreenTimeApp.appInstance.categoryTimesList as ArrayList<TimeItem>

    public fun computeCategoryUsage(context: Context, includeUnusedCategories: Boolean, includeLauncher: Boolean)
    {
        val appList = ScreenTimeApp.appInstance.appTimesList

        for (category in AppCategory.values())
        {

            val apps : ArrayList<AppTimeItem> = appList.filter{ app -> app.category == category.index } as ArrayList<AppTimeItem>
            val newCategoryItem = CategoryTimeItem(category,
                                                   apps,
                                                   context)

            //ScreenTimeApp.appInstance.addCategory(category, apps)

            updateEntry(newCategoryItem, includeUnusedCategories, this, includeLauncher)
        }
    }
}