package com.example.screentime.items

import android.app.usage.UsageStats
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.nfc.Tag
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.screentime.R
import com.example.screentime.categories.AppCategory
import com.example.screentime.utils.formatUsageTime

private const val TAG = "<-!-DEBUG-!-> com.example.screentime.items.appitem"
class AppItem (private var usageStats: UsageStats, var context: Context) : Item
{
    private val appInfo = appInfoAvailable(usageStats)

    override val itemName: String
        get() = appName()

    override val packageName: String
        get() = appInfo?.packageName ?: "none"

    override val categoryId: Int = appCategory()
    override val category: AppCategory
        get() = AppCategory.values()[categoryId + 1]

    override val icon = appIcon()
    override var useTime = usageStats.totalTimeInForeground
    override var readableUseTime: String = formatUsageTime(useTime)
    override var limit: Int = -1

    init
    {
        Log.d(TAG, "$itemName total Screentime: $useTime")
    }


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
        }
        catch (e: PackageManager.NameNotFoundException)
        {
            null
        }
    }

    override fun updateUseTime(time: Long): Long
    {
        useTime = time
        Log.d(TAG, "updated Screentime of $itemName to $useTime")
        readableUseTime = formatUsageTime(useTime)
        return useTime
    }

}

// ApplicationInfo: https://developer.android.com/reference/kotlin/android/content/pm/ApplicationInfo
// Categories : https://developer.android.com/reference/kotlin/android/content/pm/ApplicationInfo#category:kotlin.Int
