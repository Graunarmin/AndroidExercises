package com.example.screentime

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class ScreenTimeHome : AppCompatActivity()
{
    private val adapter = RecyclerAdapterAppItem()

    private lateinit var tvHomeScreenHeadline: TextView
    private lateinit var rvUsageList: RecyclerView
    private lateinit var btnRefresh: Button
    private lateinit var chkUnusedApps: CheckBox

    private var includeUnusedApps: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_time_home)

        initMembers()
        setOnClickListeners()
        setAdapter()

        getDailyUsageStats()
    }

    private fun initMembers()
    {
        rvUsageList = findViewById(R.id.rvUsageList)
        btnRefresh = findViewById(R.id.btnRefresh)
        tvHomeScreenHeadline = findViewById(R.id.tvHomeScreenHeadline)
        chkUnusedApps = findViewById(R.id.chkUnusedApps)
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
            getDailyUsageStats()
        }

        chkUnusedApps.setOnClickListener {
            if(it is CheckBox){
                includeUnusedApps = it.isChecked
                getDailyUsageStats()
            }
        }
    }

    private fun getDailyUsageStats()
    {
        val usageStatsManager: UsageStatsManager = getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1)

        val usageStatsList: List<UsageStats> = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                                                                                 calendar.timeInMillis,
                                                                                 System.currentTimeMillis())

        for (i in usageStatsList.indices)
        {
            adapter.addItem(usageStatsList[i], this.applicationContext, includeUnusedApps)
        }

    }
}