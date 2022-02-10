package com.example.screentime.adapter

import android.app.usage.UsageStats
import android.content.Context
import android.util.Log
import com.example.screentime.ScreenTimeApp
import com.example.screentime.items.AppItem
import com.example.screentime.items.Item
import com.example.screentime.items.ItemContainer

/**
 * RecyclerAdapter for [AppItem]s
 */

private const val TAG = "<DEBUG> com.example.screentime.adapter.RecyclerAdapterAppItem"
class RecyclerAdapterAppItem (itemClickListener: OnTimeItemClickListener) : RecyclerAdapterItem(itemClickListener)
{
    override var itemList: ArrayList<Item> = ArrayList()

    /**
     * Updates the list of [AppItem]s that provides the data for the RecyclerView
     */
    fun update()
    {
        itemList.clear()
        this.notifyDataSetChanged()

        itemList = ScreenTimeApp.appInstance.appList.getUsedApps() as ArrayList<Item>

        this.notifyDataSetChanged()
    }

}