package com.example.screentime

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ScreenTimeHome : AppCompatActivity()
{
    private val appAdapter = RecyclerAdapterAppItem()

    private lateinit var tvHomeScreenHeadline: TextView
    private lateinit var rvUsageList: RecyclerView
    private lateinit var btnRefresh: Button
    private lateinit var chkUnusedApps: CheckBox
    private lateinit var tglBtnApps: ToggleButton
    private lateinit var tglBtnCategories: ToggleButton
    private lateinit var vfRecyclerViews: ViewFlipper

    private var includeUnusedApps: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_time_home)

        initMembers()
        setListeners()
        setAdapter()

        getDailyUsageStats()
    }

    private fun initMembers()
    {
        rvUsageList = findViewById(R.id.rvAppUsageList)
        btnRefresh = findViewById(R.id.btnRefresh)
        tvHomeScreenHeadline = findViewById(R.id.tvTotalScreenTimeToday)
        chkUnusedApps = findViewById(R.id.chkUnusedApps)
        tglBtnApps = findViewById(R.id.tglBtnApps)
        tglBtnCategories = findViewById(R.id.tglBtnCategories)
        vfRecyclerViews = findViewById(R.id.vfRecyclerViews)
    }

    private fun setAdapter()
    {
        val layoutManager = LinearLayoutManager(this)
        rvUsageList.layoutManager = layoutManager
        rvUsageList.itemAnimator = DefaultItemAnimator()
        rvUsageList.adapter = appAdapter
        rvUsageList.setHasFixedSize(true)
    }

    private fun setListeners()
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

        tglBtnApps.setOnCheckedChangeListener {
            compoundButton, isChecked ->
            tglBtnCategories.isChecked = !isChecked
            if(isChecked)
            {
                showUsageByAppToday()

            }
            else
            {
                showUsageByCategoryToday()
            }
        }
    }

    private fun showUsageByAppToday()
    {
        getDailyUsageStats()
        vfRecyclerViews.displayedChild = 0
    }

    private fun showUsageByCategoryToday()
    {
        vfRecyclerViews.displayedChild = 1
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
            appAdapter.addItem(usageStatsList[i], this.applicationContext, includeUnusedApps)
        }

    }
}