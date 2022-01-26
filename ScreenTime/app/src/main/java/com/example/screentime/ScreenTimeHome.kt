package com.example.screentime

import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ScreenTimeHome : AppCompatActivity()
{
    private var itemList: ArrayList<ExampleItem> = ArrayList<ExampleItem>()
    private val adapter = RecyclerAdapter(itemList)

    private lateinit var tvUsageStats: TextView
    private lateinit var rvUsageList: RecyclerView
    private lateinit var btnRefresh: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMembers()
        setOnClickListeners()
        setAdapter()

        //ToDo: Sow Usage Stats from beginning
    }

    private fun initMembers()
    {
        tvUsageStats = findViewById(R.id.tvUsageStats)
        rvUsageList = findViewById(R.id.rvUsageList)
        btnRefresh = findViewById(R.id.btnRefresh)
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
            updateList(it)
        }
    }

    private fun showUsageStats()
    {
        var usageStatsManager: UsageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        var cal: Calendar = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_MONTH, -1)
        var queryUsageStats : List<UsageStats> = usageStatsManager.queryUsageStats(
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
        // tvUsageStats.text = statsData
    }

    public fun updateList(view: View)
    {
        showUsageStats()
        // better use notifyItemInserted() or sth. like that https://www.youtube.com/watch?v=XyQvoONPMng
        // and also put in Adapter Class
        adapter.notifyDataSetChanged()
    }

    private fun convertTime(lastTimeUsed: Long): String
    {
        var date: Date = Date(lastTimeUsed)
        var format: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
        return format.format(date)
    }

    private fun setAppInfo(appName: String, appTime: String)
    {
        //Put Functionality in Adapter Class
        itemList.add(ExampleItem(R.drawable.ic_android, appName, appTime))
    }
}