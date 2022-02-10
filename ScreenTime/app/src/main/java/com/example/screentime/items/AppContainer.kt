package com.example.screentime.items

import android.app.usage.UsageStats

/**
 * Inherits from the [ItemContainer], specifically handles the updating of the usage time for [AppItem]s
 * and other [AppItem]-specific Actions
 */
private const val TAG = "com.example.screentime.items.appcontainer"
class AppContainer : ItemContainer()
{
    override fun updateUsageStats(usageStats: UsageStats) : Boolean
    {
        val itemToUpdate = itemList.find { item -> item.packageName == usageStats.packageName }
        if(itemToUpdate != null)
        {
            itemToUpdate.updateUseTime(usageStats.totalTimeInForeground)
            return true
        }
        return false
    }

    /**
     * Retrieves a List of only those apps the were actually used
     */
    fun getUsedApps() : ArrayList<AppItem>
    {
        return ArrayList(itemList.filter { app -> app.wasUsed }
                             .sortedBy { it.useTime }
                             .reversed()) as ArrayList<AppItem>
    }

    /**
     * Retrieves a List of only those apps for which a limit was set
     */
    fun getAppsWithLimit(): ArrayList<AppItem>
    {
        return ArrayList(itemList.filter { app -> app.limit > 0 }
                             .sortedBy { it.itemName }
                             .reversed()) as ArrayList<AppItem>
    }
}