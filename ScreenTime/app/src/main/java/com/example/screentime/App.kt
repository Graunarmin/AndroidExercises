package com.example.screentime

import android.app.usage.UsageStats
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import java.util.concurrent.TimeUnit

data class App (var usageStats: UsageStats, var context: Context)
{
    private val appInfo = appInfoAvailable(usageStats)

    var appName = appName()
    var appIcon = appIcon()
    var appCategory = appCategory()
    val useTime = usageStats.totalTimeInForeground
    val readableUseTime = formatUsageTime()

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

    private fun appCategory(): String
    {
        if(appInfo != null){
            return appInfo.category.toString()
        }
        return (-1).toString()
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

    private fun formatUsageTime(): String
    {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(useTime),
                             TimeUnit.MILLISECONDS.toMinutes(useTime) % TimeUnit.HOURS.toMinutes(1),
                             TimeUnit.MILLISECONDS.toSeconds(useTime) % TimeUnit.MINUTES.toSeconds(1))
    }


}

// ApplicationInfo: https://developer.android.com/reference/kotlin/android/content/pm/ApplicationInfo
// Categories : https://developer.android.com/reference/kotlin/android/content/pm/ApplicationInfo#category:kotlin.Int
