package com.example.screentime

import android.app.Application
import com.example.screentime.items.AppItem
import com.example.screentime.utils.formatUsageTime
import com.example.screentime.utils.hourMinFormat
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
        totalScreenTime = hourMinFormat(time)
    }

}