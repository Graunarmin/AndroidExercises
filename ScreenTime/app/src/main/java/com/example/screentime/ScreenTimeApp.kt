package com.example.screentime

import android.app.Application
import android.content.pm.ApplicationInfo
import com.example.screentime.categories.AppCategory
import com.example.screentime.categories.CategoriesList
import com.example.screentime.timeitems.AppTimeItem
import com.example.screentime.timeitems.CategoryTimeItem
import com.example.screentime.utils.hourMinFormat
import java.util.*
import kotlin.collections.ArrayList

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

    var appTimesList: ArrayList<AppTimeItem> = ArrayList<AppTimeItem>()
    var categoryTimesList: ArrayList<CategoryTimeItem> = ArrayList<CategoryTimeItem>()

    var appList: ArrayList<AppTimeItem> = ArrayList()
    lateinit var categoriesList: CategoriesList


    var totalScreenTime: String = ""

    public fun updateTotalScreenTime()
    {
        var time: Long = 0
        appTimesList.forEach { app ->  time += app.useTime}
        totalScreenTime = hourMinFormat(time)
    }

}