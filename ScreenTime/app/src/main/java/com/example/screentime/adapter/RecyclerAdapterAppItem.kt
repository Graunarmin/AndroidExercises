package com.example.screentime.adapter

import android.app.usage.UsageStats
import android.content.Context
import android.util.Log
import com.example.screentime.ScreenTimeApp
import com.example.screentime.items.AppItem
import com.example.screentime.items.Item
import com.example.screentime.items.ItemContainer

private const val TAG = "<DEBUG> com.example.screentime.adapter.RecyclerAdapterAppItem"
class RecyclerAdapterAppItem (itemClickListener: OnTimeItemClickListener) : RecyclerAdapterItem(itemClickListener)
{
    override var itemList: ArrayList<Item> = ArrayList()

    fun update()
    {
        var size = itemList.size
        itemList.clear()
        this.notifyItemRangeRemoved(0, size-1)

        itemList = ScreenTimeApp.appInstance.appList.getUsedApps() as ArrayList<Item>

        size = itemList.size
        this.notifyItemRangeInserted(0, size-1)
    }

}