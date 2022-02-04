package com.example.screentime.items

import android.app.usage.UsageStats
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.screentime.R

data class AppItem (var usageStats: UsageStats, var context: Context) : Item
{
    private val appInfo = appInfoAvailable(usageStats)

    override var name = appName()
    override var icon = appIcon()
    var category = appCategory()
    override val useTime = usageStats.totalTimeInForeground

    private fun appName(): String
    {
        if(appInfo != null)
        {
            return context.packageManager.getApplicationLabel(appInfo).toString()
        }
        return usageStats.packageName.split(".").last()
    }

    private fun appIcon(): Drawable?
    {
        if(appInfo != null){
            return context.packageManager.getApplicationIcon(appInfo)
        }
        return ContextCompat.getDrawable(context, R.drawable.ic_android)
    }

    private fun appCategory(): Int
    {
        if(appInfo != null){
            return appInfo.category
        }
        return -1
    }

    private fun appInfoAvailable(usageStats: UsageStats) : ApplicationInfo?
    {
        return try
        {
            context.packageManager.getApplicationInfo(usageStats.packageName,0)
        }catch (e: PackageManager.NameNotFoundException)
        {
            null
        }
    }

}

// ApplicationInfo: https://developer.android.com/reference/kotlin/android/content/pm/ApplicationInfo
// Categories : https://developer.android.com/reference/kotlin/android/content/pm/ApplicationInfo#category:kotlin.Int
