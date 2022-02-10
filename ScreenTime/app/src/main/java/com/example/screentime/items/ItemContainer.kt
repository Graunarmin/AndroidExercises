package com.example.screentime.items

import android.annotation.SuppressLint
import android.app.usage.UsageStats
import android.util.Log
import com.example.screentime.adapter.RecyclerAdapterItem

/**
 * Container that holds a List of [Item]s and performs actions on it.
 */

private const val TAG = "com.example.screentime.items.itemcontainer"
open class ItemContainer
{
    open var itemList : ArrayList<Item> = ArrayList()

    val totalItemUseTime: Long
        get()
        {
            var time: Long = 0
            itemList.forEach { app ->  time += app.useTime}
            return time
        }

    val size : Int
        get() { return itemList.size }

    private fun add(item: Item)
    {
        itemList.add(item)
    }

    private fun add(index: Int, item: Item)
    {
        itemList.add(index, item)
    }

    private fun remove(item: Item)
    {
        itemList.remove(item)
    }

    private fun remove(index: Int)
    {
        itemList.removeAt(index)
    }

    private fun containsItemWithName(name: String) : Boolean
    {
        if(itemList.find { item -> item.itemName == name } != null)
        {
            return true
        }
        return false
    }

    fun getItemAt(index: Int) : Item
    {
        return itemList[index]
    }

    fun getItemByName(name: String) : Item?
    {
        return itemList.find { item -> item.itemName == name }
    }

    fun getItemLimit(name: String) : Int
    {
        return getItemByName(name)?.limit ?: -1
    }

    fun getIndexOfItem(name: String) : Int
    {
        val item: Item? = getItemByName(name)
        if(item != null)
        {
            return itemList.indexOf(item)
        }
        else return -1
    }

    private fun getNewEntryIndex(itemToInsert: Item): Int
    {
        for (item: Item in itemList)
        {
            if(item < itemToInsert)
            {
                return itemList.indexOf(item)
            }
        }
        return itemList.size
    }

    fun getAllItemsOfCategory(category: Int) : ArrayList<Item>
    {
        return ArrayList(itemList.filter { item -> item.categoryId == category })
    }

    fun addIfNotContained(item: Item){
        if(item.itemName == "Pixel Launcher")
        {
            return
        }
        if(!containsItemWithName(item.itemName))
        {
            add(item)
        }
    }

    private fun addIfNotContained(item: Item, index: Int)
    {
        if(item.itemName == "Pixel Launcher"){
            return
        }
        if(!containsItemWithName(item.itemName))
        {
            add(index, item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearList(adapter: RecyclerAdapterItem)
    {
        itemList.clear()
        with(adapter) { notifyDataSetChanged() }
    }

    open fun updateUsageStats(usageStats: UsageStats) : Boolean
    {
        return false
    }
}