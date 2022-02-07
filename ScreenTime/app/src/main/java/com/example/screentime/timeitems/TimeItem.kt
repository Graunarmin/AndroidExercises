package com.example.screentime.timeitems

import android.graphics.drawable.Drawable
import com.example.screentime.utils.formatUsageTime

interface TimeItem
{
    val name: String
    val icon: Drawable?
    val useTime: Long
    val readableUseTime: String
        get() = formatUsageTime(useTime)
    val wasUsed: Boolean
        get() = (useTime != 0L)

    operator fun compareTo(other: TimeItem): Int
    {
        return compareValues(useTime, other.useTime)
    }

}