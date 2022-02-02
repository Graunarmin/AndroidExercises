package com.example.screentime

import android.graphics.drawable.Drawable

data class App (val appIcon: Drawable?, val appName: String, val usageTime: String, val category: Int)
{
    
}

// ApplicationInfo: https://developer.android.com/reference/kotlin/android/content/pm/ApplicationInfo
// Categories : https://developer.android.com/reference/kotlin/android/content/pm/ApplicationInfo#category:kotlin.Int
