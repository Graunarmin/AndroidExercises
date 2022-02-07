package com.example.screentime.adapter

import android.app.usage.UsageStats
import android.content.Context
import com.example.screentime.ScreenTimeApp
import com.example.screentime.timeitems.AppTimeItem
import com.example.screentime.timeitems.TimeItem

private const val TAG = "<DEBUG> com.example.screentime.appcategories.RecyclerAdapterAppItem"
class RecyclerAdapterAppItem (itemClickListener: OnTimeItemClickListener) : RecyclerAdapterItem(itemClickListener)
{
    override var itemList: ArrayList<TimeItem> = ScreenTimeApp.appInstance.appTimesList as ArrayList<TimeItem>

    public fun addItem(usageStats: UsageStats, context: Context, includeUnusedApps: Boolean, includeLauncer: Boolean)
    {
        // Create new app item
        val newAppItem = AppTimeItem(usageStats, context)

        updateEntry(newAppItem, includeUnusedApps, this, includeLauncer)
    }

}