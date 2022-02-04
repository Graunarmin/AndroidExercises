package com.example.screentime

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.screentime.recycleadapter.RecyclerAdapterAppItem
import com.example.screentime.recycleadapter.RecyclerAdapterCategoryItem
import java.util.*

private const val TAG = "<DEBUG> com.example.screentime.ScreenTimeHome"
class ScreenTimeHome : AppCompatActivity()
{
    private val appAdapter = RecyclerAdapterAppItem()
    private val categoryAdapter = RecyclerAdapterCategoryItem()

    private lateinit var tvDateHeadline: TextView
    private lateinit var tvTotalScreenTimeToday: TextView
    private lateinit var tglBtnApps: ToggleButton
    private lateinit var tglBtnCategories: ToggleButton
    private lateinit var vfRecyclerViews: ViewFlipper
    private lateinit var rvAppUsageList: RecyclerView
    private lateinit var rvCategoryUsageList: RecyclerView
    private lateinit var btnRefresh: Button
    private lateinit var chkUnusedApps: CheckBox

    private var includeUnusedApps: Boolean = false
    private var showApps: Boolean = true
    private var showCategories: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_time_home)

        initMembers()
        setListeners()
        setAdapter()

        startPosition()
    }

    private fun initMembers()
    {
        tvDateHeadline = findViewById(R.id.tvDateHeadline)
        tvTotalScreenTimeToday = findViewById(R.id.tvTotalScreenTimeToday)
        tglBtnApps = findViewById(R.id.tglBtnApps)
        tglBtnCategories = findViewById(R.id.tglBtnCategories)
        vfRecyclerViews = findViewById(R.id.vfRecyclerViews)
        rvAppUsageList = findViewById(R.id.rvAppUsageList)
        rvCategoryUsageList = findViewById(R.id.rvCategoryUsageList)
        btnRefresh = findViewById(R.id.btnRefresh)
        chkUnusedApps = findViewById(R.id.chkUnusedApps)
    }

    private fun setAdapter()
    {
        val layoutManagerApps = LinearLayoutManager(this)
        rvAppUsageList.layoutManager = layoutManagerApps
        rvAppUsageList.itemAnimator = DefaultItemAnimator()
        rvAppUsageList.adapter = appAdapter
        rvAppUsageList.setHasFixedSize(true)

        val layoutManagerCat = LinearLayoutManager(this)
        rvCategoryUsageList.layoutManager = layoutManagerCat
        rvCategoryUsageList.itemAnimator = DefaultItemAnimator()
        rvCategoryUsageList.adapter = categoryAdapter
        rvCategoryUsageList.setHasFixedSize(true)
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
            showApps = isChecked

            if(showApps)
            {
                if(showCategories)
                {
                    tglBtnCategories.isChecked = !isChecked
                }
                vfRecyclerViews.displayedChild = 0
                getDailyUsageStats()
            }

        }

        tglBtnCategories.setOnCheckedChangeListener {
            compoundButton, isChecked ->
            showCategories = isChecked

            if(showCategories)
            {
                if(showApps)
                {
                    tglBtnApps.isChecked = !isChecked
                }
                vfRecyclerViews.displayedChild = 1
                getDailyUsageStats()
            }

        }
    }

    private fun startPosition()
    {
        tglBtnApps.isChecked = true
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

        if(showCategories)
        {
            categoryAdapter.computeCategoryUsage(this.applicationContext)
        }


    }
}