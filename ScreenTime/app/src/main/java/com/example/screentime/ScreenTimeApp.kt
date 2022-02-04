package com.example.screentime

import android.app.Application
import com.example.screentime.items.AppItem

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

}