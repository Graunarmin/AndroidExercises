package com.example.screentime

import android.app.Application

class ScreenTimeApp(data: String) : Application()
{
    companion object
    {
        lateinit var appInstance: ScreenTimeApp
    }

    init
    {
        appInstance = this
    }

    var appList: ArrayList<App> = ArrayList<App>()

    fun addEvent(){}
}