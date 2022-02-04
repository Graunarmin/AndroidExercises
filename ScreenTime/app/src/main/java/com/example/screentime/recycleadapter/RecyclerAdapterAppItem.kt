package com.example.screentime.recycleadapter

import android.app.usage.UsageStats
import android.content.Context
import com.example.screentime.items.AppItem

private const val TAG = "<DEBUG> com.example.screentime.appcategories.RecyclerAdapterAppItem"
class RecyclerAdapterAppItem () : RecyclerAdapterItem()
{

    public fun addItem(usageStats: UsageStats, context: Context, includeUnusedApps: Boolean)
    {
        // Create new app item
        val newAppItem = AppItem(usageStats, context)

        // Check if item is already in list (by name)
        var index = itemList.indexOf(itemList.find { x -> x.name == newAppItem.name })

        // index is -1 if Item was not in List
        if(index != -1)
        {
            // remove the entry
            itemList.removeAt(index)
            // then notify change at removed position
            this.notifyItemRemoved(index);
        }

        if(!includeUnusedApps)
        {
            if(usageStats.totalTimeInForeground == (0).toLong())
            {
                return
            }
        }

        // find out where in the List the new entry fits (sorted descending by usage time)
        index = getNewItemIndex(newAppItem)

        // add new entry
        itemList.add(index, newAppItem)

        // then notify change at added position
        this.notifyItemInserted(index)
    }

}