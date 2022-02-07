package com.example.screentime

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.screentime.charts.PieChartCreator
import com.example.screentime.timeitems.TimeItem
import com.example.screentime.adapter.OnTimeItemClickListener
import com.example.screentime.adapter.RecyclerAdapterAppItem
import com.example.screentime.adapter.RecyclerAdapterCategoryItem
import com.example.screentime.categories.CategoriesList
import com.example.screentime.utils.dayMonthFormat
import com.github.mikephil.charting.charts.PieChart
import java.time.LocalDate
import java.util.*

private const val TAG = "<DEBUG> com.example.screentime.ScreenTimeOverview"
class ScreenTimeOverviewActivity : AppCompatActivity(), OnTimeItemClickListener
{
    private val appAdapter = RecyclerAdapterAppItem(this)
    private val categoryAdapter = RecyclerAdapterCategoryItem(this)
    private val pieChartCreator : PieChartCreator = PieChartCreator()

    private lateinit var tvCurrentDate: TextView
    private lateinit var chartOverviewToday: PieChart
    private lateinit var btnTogglMostUsed: Button
    private lateinit var vfRecyclerViews: ViewFlipper
    private lateinit var rvAppUsageList: RecyclerView
    private lateinit var rvCategoryUsageList: RecyclerView
    private lateinit var chkIncludeUnused: CheckBox
    private lateinit var chkIncludePixelLauncher: CheckBox
    private lateinit var btnEditLimits : Button

    private var showApps: Boolean = true
    private var showCategories: Boolean = !showApps
    private var includeUnused: Boolean = false
    private var includePixelLauncher: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_time_overview)

        initMembers()
        setListeners()
        setAdapter()

        showMostUsed()
        pieChartCreator.createPieChart(chartOverviewToday,
                                       ScreenTimeApp.appInstance.totalScreenTime,
                                       this.applicationContext)
        createCategoriesList()
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
        chkIncludePixelLauncher = findViewById(R.id.chkIncludePixelLauncher)
        btnEditLimits = findViewById(R.id.btnEditLimits)
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

        chkIncludePixelLauncher.setOnClickListener {
            if(it is CheckBox){
                includePixelLauncher = it.isChecked
                getDailyUsageStats()
            }
        }

        btnTogglMostUsed.setOnClickListener {
            showCategories = !showCategories
            showApps = !showApps

            showMostUsed()
        }

        btnEditLimits.setOnClickListener {
            startNewIntent("","", LimitActivity::class.java)
        }
    }

    override fun onTimeItemClicked(item: TimeItem)
    {
        startNewIntent("","", AppDetailsActivity::class.java)
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
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1)

        val usageStatsList: List<UsageStats> = getUsageStats(UsageStatsManager.INTERVAL_DAILY, calendar)

        for (i in usageStatsList.indices)
        {
            appAdapter.addItem(usageStatsList[i], this.applicationContext, includeUnused, includePixelLauncher)
        }

        categoryAdapter.computeCategoryUsage(this.applicationContext, includeUnused, includePixelLauncher)


        ScreenTimeApp.appInstance.updateTotalScreenTime()
        tvCurrentDate.text = dayMonthFormat(LocalDate.now())

        chartOverviewToday.notifyDataSetChanged()
    }

    private fun getUsageStats(intervalType: Int, calendar: Calendar): List<UsageStats>
    {
        val usageStatsManager: UsageStatsManager =
                getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager

        return usageStatsManager.queryUsageStats(intervalType,
                                                 calendar.timeInMillis,
                                                 System.currentTimeMillis()
        )
    }

    private fun createCategoriesList()
    {
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1)

        ScreenTimeApp.appInstance.categoriesList =
                CategoriesList(getUsageStats(UsageStatsManager.INTERVAL_DAILY, calendar),
                               this.applicationContext)

    }

    private fun startNewIntent(tag: String, message: String, newActivity: Class<*>?){
        Intent(this, newActivity).also {
            startActivity(it)
        }

    }


}

// https://stonesoupprogramming.com/2017/11/17/kotlin-string-formatting/
