package com.example.screentime.items

import android.app.usage.UsageStats

/**
 * Inherits from the [ItemContainer], specifically handles the updating of the usage time for [CategoryItem]s
 * and other [CategoryItem]-specific Actions
 */

private const val TAG = "com.example.screentime.items.categorycontainer"
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

    /**
     * Retrieves a List of only those categories the were actually used
     */
    fun getUsedCategories(): ArrayList<CategoryItem>
    {
        return ArrayList(itemList.filter { category -> category.wasUsed }.sortedBy { it.useTime }
                             .reversed()) as ArrayList<CategoryItem>
    }
}