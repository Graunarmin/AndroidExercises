package com.example.screentime.items

import android.app.usage.UsageStats

private const val TAG = "<DEBUG> com.example.screentime.items.categorycontainer"
class CategoryContainer : ItemContainer()
{
    override fun updateUsageStats(usageStats: UsageStats) : Boolean
    {
        for (item in itemList)
        {
            item.updateUseTime(0)
        }
        return true
    }

    fun getUsedCategories(): ArrayList<CategoryItem>
    {
        return ArrayList(itemList.filter { category -> category.wasUsed }.sortedBy { it.useTime }
                             .reversed()) as ArrayList<CategoryItem>
    }
}