package com.example.screentime.items

import android.graphics.drawable.Drawable
import com.example.screentime.categories.AppCategory
import com.example.screentime.utils.formatUsageTime

/**
 * Interface for items that have a use time and a limit.
 */
private const val TAG = "com.example.screentime.items.item"
interface Item
{
    val itemName: String
    val packageName: String
    val icon: Drawable?
    var useTime: Long
    var readableUseTime: String
    var wasUsed: Boolean

    val categoryId: Int

    val category : AppCategory

    var limit: Int

    operator fun compareTo(other: Item): Int
    {
        return compareValues(useTime, other.useTime)
    }

    fun updateUseTime(time: Long) : Long
}