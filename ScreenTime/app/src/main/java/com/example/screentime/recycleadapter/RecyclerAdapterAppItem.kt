package com.example.screentime.recycleadapter

import android.app.usage.UsageStats
import android.content.Context
import com.example.screentime.ScreenTimeApp
import com.example.screentime.items.AppItem
import com.example.screentime.items.Item

private const val TAG = "<DEBUG> com.example.screentime.appcategories.RecyclerAdapterAppItem"
class RecyclerAdapterAppItem () : RecyclerAdapterItem()
{
    override var itemList: ArrayList<Item> = ScreenTimeApp.appInstance.appList as ArrayList<Item>

    public fun addItem(usageStats: UsageStats, context: Context, includeUnusedApps: Boolean)
    {
        // Create new app item
        val newAppItem = AppItem(usageStats, context)

        updateEntry(newAppItem, includeUnusedApps, this)
    }

}