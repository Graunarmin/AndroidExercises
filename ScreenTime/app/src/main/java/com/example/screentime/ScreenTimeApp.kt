package com.example.screentime

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.screentime.categories.CategoriesMap
import com.example.screentime.items.AppContainer
import com.example.screentime.items.CategoryContainer
import com.example.screentime.items.ItemContainer
import com.example.screentime.utils.hourMinFormat

/**
 * Singleton that provides access to the [ItemContainer]s of AppItems and CategoryItems
 * for all activities
 * Next Version: Replace Singleton with database
 */

const val  channelID = "channel_limit_notification"
const val channelName = "limits_channel"
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

    // Holds all Categories mapped to the apps in that category
    lateinit var categoriesMap: CategoriesMap

    var totalScreenTime: String = ""

    override fun onCreate()
    {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels()
    {
        val limitsChannel: NotificationChannel = NotificationChannel(
                channelID,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
        )

        limitsChannel.description = "Informs the user that the time limit of an App has been reached"

        val notificationManager : NotificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(limitsChannel)
    }

    fun updateTotalScreenTime()
    {
        totalScreenTime = hourMinFormat(appList.totalItemUseTime)
    }

}