package com.example.screentime

import android.app.Application
import com.example.screentime.items.AppItem
import java.util.concurrent.TimeUnit

class ScreenTimeApp : Application()
{
    companion object
    {
        lateinit var appInstance: ScreenTimeApp
    }

    init
    {
        appInstance = this
    }

    var appList: ArrayList<AppItem> = ArrayList<AppItem>()
    var totalScreenTime: String = ""

    public fun updateTotalScreenTime()
    {
        var time: Long = 0
        appList.forEach { app ->  time += app.useTime}
        totalScreenTime = formatUsageTime(time)
    }

    fun formatUsageTime(time: Long): String
    {
        return String.format("%dh %02dm", TimeUnit.MILLISECONDS.toHours(time),
                             TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1))
    }
}