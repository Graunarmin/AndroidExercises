package com.example.screentime.items

import android.graphics.drawable.Drawable
import com.example.screentime.utils.formatUsageTime
import com.github.mikephil.charting.data.PieEntry
import java.util.concurrent.TimeUnit

interface Item
{
    val name: String
    val icon: Drawable?
    val useTime: Long
    val readableUseTime: String
        get() = formatUsageTime(useTime)
    val wasUsed: Boolean
        get() = (useTime != 0L)

    operator fun compareTo(other: Item): Int
    {
        return compareValues(useTime, other.useTime)
    }

}