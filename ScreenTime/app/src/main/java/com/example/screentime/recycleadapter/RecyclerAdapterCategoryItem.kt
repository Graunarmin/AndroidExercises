package com.example.screentime.recycleadapter

import android.content.Context
import com.example.screentime.items.AppCategory
import com.example.screentime.ScreenTimeApp
import com.example.screentime.items.AppItem
import com.example.screentime.items.CategoryItem

private const val TAG = "<DEBUG> com.example.screentime.appcategories.RecyclerAdapterCategories"
class RecyclerAdapterCategoryItem() : RecyclerAdapterItem()
{
    public fun computeCategoryUsage(context: Context, includeUnusedCategories: Boolean)
    {
        val appList = ScreenTimeApp.appInstance.appList

        for (category in AppCategory.values())
        {

            val newCategoryItem = CategoryItem(category,
                                               appList.filter{ app -> app.category == category.index } as ArrayList<AppItem>,
                                               context)

            updateEntry(newCategoryItem, includeUnusedCategories, this)
        }
    }
}