package com.example.screentime.items

import android.graphics.drawable.Drawable
import java.util.concurrent.TimeUnit

interface Item
{
    val name: String
    val icon: Drawable?
    val useTime: Long
    val readableUseTime: String
        get() = formatUsageTime()



    fun formatUsageTime(): String
    {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(useTime),
                             TimeUnit.MILLISECONDS.toMinutes(useTime) % TimeUnit.HOURS.toMinutes(1),
                             TimeUnit.MILLISECONDS.toSeconds(useTime) % TimeUnit.MINUTES.toSeconds(1))
    }

    operator fun compareTo(other: Item): Int
    {
        return compareValues(useTime, other.useTime)
    }


}