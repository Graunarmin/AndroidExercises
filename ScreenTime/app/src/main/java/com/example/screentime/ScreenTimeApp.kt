package com.example.screentime

import android.app.Application
import com.example.screentime.categories.CategoriesMap
import com.example.screentime.items.AppContainer
import com.example.screentime.items.CategoryContainer
import com.example.screentime.items.ItemContainer
import com.example.screentime.utils.hourMinFormat

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

    // This is the "main" List that holds all of the installed apps and the use limits
    var appList: AppContainer = AppContainer()
    var categoryList: CategoryContainer = CategoryContainer()

    //Holds all Categories mapped to the apps in that category
    lateinit var categoriesMap: CategoriesMap


    var totalScreenTime: String = ""

    fun updateTotalScreenTime()
    {
        totalScreenTime = hourMinFormat(appList.totalItemUseTime)
    }

}