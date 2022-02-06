package com.example.screentime

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.screentime.charts.PieChartCreator
import com.example.screentime.recycleadapter.RecyclerAdapterAppItem
import com.example.screentime.recycleadapter.RecyclerAdapterCategoryItem
import com.example.screentime.utils.dayMonthFormat
import com.github.mikephil.charting.charts.PieChart
import java.time.LocalDate
import java.util.*

private const val TAG = "<DEBUG> com.example.screentime.ScreenTimeOverview"
class ScreenTimeOverviewActivity : AppCompatActivity()
{
    private val appAdapter = RecyclerAdapterAppItem()
    private val categoryAdapter = RecyclerAdapterCategoryItem()
    private val pieChartCreator : PieChartCreator = PieChartCreator()

    private lateinit var tvCurrentDate: TextView
    private lateinit var chartOverviewToday: PieChart
    private lateinit var btnTogglMostUsed: Button
    private lateinit var vfRecyclerViews: ViewFlipper
    private lateinit var rvAppUsageList: RecyclerView
    private lateinit var rvCategoryUsageList: RecyclerView
    private lateinit var chkIncludeUnused: CheckBox

    private var showApps: Boolean = true
    private var showCategories: Boolean = !showApps
    private var includeUnused: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_time_overview)

        initMembers()
        setListeners()
        setAdapter()

        showMostUsed()
        pieChartCreator.createPieChart(chartOverviewToday, ScreenTimeApp.appInstance.totalScreenTime, this.applicationContext)
    }

    private fun initMembers()
    {
        tvCurrentDate = findViewById(R.id.tvCurrentDate)
        chartOverviewToday = findViewById(R.id.chartOverviewToday)
        btnTogglMostUsed = findViewById(R.id.btnTglMostUsed)
        vfRecyclerViews = findViewById(R.id.vfRecyclerViews)
        rvAppUsageList = findViewById(R.id.rvAppUsageList)
        rvCategoryUsageList = findViewById(R.id.rvCategoryUsageList)
        chkIncludeUnused = findViewById(R.id.chkIncludeUnused)
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
        chkIncludeUnused.setOnClickListener {
            if(it is CheckBox){
                includeUnused = it.isChecked
                getDailyUsageStats()
            }
        }

        btnTogglMostUsed.setOnClickListener {
            showCategories = !showCategories
            showApps = !showApps

            showMostUsed()
        }
    }

    private fun showMostUsed()
    {
        if(showApps)
        {
            btnTogglMostUsed.text = getString(R.string.toggle_Categories)
            vfRecyclerViews.displayedChild = 0
        }else
        {
            btnTogglMostUsed.text = getString(R.string.toggle_Apps)
            vfRecyclerViews.displayedChild = 1
        }
        getDailyUsageStats()
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
            appAdapter.addItem(usageStatsList[i], this.applicationContext, includeUnused)
        }


        //if(showCategories)
        //{
            categoryAdapter.computeCategoryUsage(this.applicationContext, includeUnused)
        //}

        ScreenTimeApp.appInstance.updateTotalScreenTime()
        tvCurrentDate.text = dayMonthFormat(LocalDate.now())

        chartOverviewToday.notifyDataSetChanged()
    }
}

// https://stonesoupprogramming.com/2017/11/17/kotlin-string-formatting/
