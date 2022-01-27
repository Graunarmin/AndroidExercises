package com.example.screentime

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class ScreenTimeHome : AppCompatActivity()
{
    private val adapter = RecyclerAdapter()

    private lateinit var tvHomeScreenHeadline: TextView
    private lateinit var rvUsageList: RecyclerView
    private lateinit var btnRefresh: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_time_home)

        initMembers()
        setOnClickListeners()
        setAdapter()

        showUsageStats()
    }

    private fun initMembers()
    {
        rvUsageList = findViewById(R.id.rvUsageList)
        btnRefresh = findViewById(R.id.btnRefresh)
        tvHomeScreenHeadline = findViewById(R.id.tvHomeScreenHeadline)
    }

    private fun setAdapter()
    {
        val layoutManager = LinearLayoutManager(this)
        rvUsageList.layoutManager = layoutManager
        rvUsageList.itemAnimator = DefaultItemAnimator()
        rvUsageList.adapter = adapter
        rvUsageList.setHasFixedSize(true)
    }

    private fun setOnClickListeners()
    {
        btnRefresh.setOnClickListener{
            showUsageStats()
        }
    }

    private fun showUsageStats()
    {
        val usageStatsManager: UsageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val cal: Calendar = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_MONTH, -1)
        val queryUsageStats : List<UsageStats> = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                cal.timeInMillis,
                System.currentTimeMillis())

        var statsData : String = ""
        var appName: String = ""
        var appTime: String = ""
        for (i in queryUsageStats.indices)

        // ToDo: Show App Name (not Package) + Icon
        // ToDo: Compute total Usage time for the current day
        {
            statsData = statsData + "Package Name: " + queryUsageStats[i].packageName + "\n" +
                        "Last Time Used: " + convertTime(queryUsageStats[i].lastTimeUsed) + "\n" +
                        "Describe Contents: " + queryUsageStats[i].describeContents() + "\n" +
                        "First Time Stamp: " + convertTime(queryUsageStats[i].firstTimeStamp) + "\n" +
                        "Last Time Stamp: " + convertTime(queryUsageStats[i].lastTimeStamp) + "\n" +
                        "Total Time in Foreground: " + convertTime(queryUsageStats[i].totalTimeInForeground) + "\n"

            appName = queryUsageStats[i].packageName
            appTime = convertTime(queryUsageStats[i].totalTimeInForeground)

            setAppInfo(appName, appTime)
        }
    }

    private fun convertTime(lastTimeUsed: Long): String
    {
        val date: Date = Date(lastTimeUsed)
        val format: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
        return format.format(date)
    }

    private fun setAppInfo(appName: String, appTime: String)
    {
        adapter.addItem(R.drawable.ic_android, appName, appTime)
    }
}