package com.example.screentime.items

import android.app.usage.UsageStats
import android.util.Log

private const val TAG = "<DEBUG> com.example.screentime.items.appcontainer"
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

    fun getUsedApps() : ArrayList<AppItem>
    {
        return ArrayList(itemList.filter { app -> app.wasUsed }.sortedBy { it.useTime }.reversed()) as ArrayList<AppItem>
    }
}