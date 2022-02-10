package com.example.screentime.adapter

import android.content.Context
import com.example.screentime.categories.AppCategory
import com.example.screentime.ScreenTimeApp
import com.example.screentime.items.AppItem
import com.example.screentime.items.CategoryItem
import com.example.screentime.items.Item
import com.example.screentime.items.ItemContainer

/**
 * Recycler Adapter for [CategoryItem]s
 */

private const val TAG = "<DEBUG> com.example.screentime.adapter.RecyclerAdapterCategories"
class RecyclerAdapterCategoryItem(itemClickListener: OnTimeItemClickListener) : RecyclerAdapterItem(itemClickListener)
{
    override var itemList: ArrayList<Item> = ArrayList()

    /**
     * Updates the list of [CategoryItem]s that provides the data for the RecyclerView
     */

    fun update()
    {
        var size = itemList.size
        itemList.clear()
        this.notifyItemRangeRemoved(0, size)

        itemList = ScreenTimeApp.appInstance.categoryList.getUsedCategories() as ArrayList<Item>

        size = itemList.size
        this.notifyItemRangeInserted(0, size)

    }
}