package com.example.screentime

import android.app.Notification
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.screentime.charts.PieChartCreator
import com.example.screentime.items.Item
import com.example.screentime.adapter.OnTimeItemClickListener
import com.example.screentime.adapter.RecyclerAdapterAppItem
import com.example.screentime.adapter.RecyclerAdapterCategoryItem
import com.example.screentime.adapter.RecyclerAdapterLimit
import com.example.screentime.categories.AppCategory
import com.example.screentime.items.AppItem
import com.example.screentime.items.CategoryItem
import com.example.screentime.utils.dayMonthFormat
import com.example.screentime.utils.longMillisToFloatMinutes
import com.github.mikephil.charting.charts.PieChart
import java.time.LocalDate
import java.util.*

/**
 * Central Focus Point of the App
 */
private const val TAG = "com.example.screentime.ScreenTimeOverviewActivity"
const val EXTRA_APP_NAME_FOR_DETAILS = "EXTRA_APP_NAME_FOR_DETAILS"
class ScreenTimeOverviewActivity : AppCompatActivity(), OnTimeItemClickListener
{
    private val appAdapter = RecyclerAdapterAppItem(this)
    private val categoryAdapter = RecyclerAdapterCategoryItem(this)
    private val limitAdapter = RecyclerAdapterLimit()
    private val pieChartCreator : PieChartCreator = PieChartCreator()

    private lateinit var tvCurrentDate: TextView
    private lateinit var chartOverviewToday: PieChart
    private lateinit var btnTogglMostUsed: Button
    private lateinit var vfRecyclerViews: ViewFlipper
    private lateinit var rvAppUsageList: RecyclerView
    private lateinit var rvCategoryUsageList: RecyclerView
    private lateinit var rvLimitsOverview: RecyclerView
    private lateinit var btnEditLimits : Button

    private var showApps: Boolean = true
    private var showCategories: Boolean = !showApps
    private lateinit var notificationManager: NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_time_overview)

        initMembers()
        setListeners()
        setAdapter()

        getAllApps()
        getAllCategories()
    }

    override fun onResume()
    {
        super.onResume()
        limitAdapter.update()


        showMostUsed()
        pieChartCreator.createPieChart(chartOverviewToday,
                                       ScreenTimeApp.appInstance.totalScreenTime,
                                       this.applicationContext)
        checkLimits()
    }

    private fun initMembers()
    {
        tvCurrentDate = findViewById(R.id.tvCurrentDate)
        chartOverviewToday = findViewById(R.id.chartOverviewToday)
        btnTogglMostUsed = findViewById(R.id.btnTglMostUsed)
        vfRecyclerViews = findViewById(R.id.vfRecyclerViews)
        rvAppUsageList = findViewById(R.id.rvAppUsageList)
        rvCategoryUsageList = findViewById(R.id.rvCategoryUsageList)
        rvLimitsOverview = findViewById(R.id.rvLimitsOverview)
        btnEditLimits = findViewById(R.id.btnEditLimits)

        notificationManager = NotificationManagerCompat.from(this)
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

        val layoutManagerLimit = LinearLayoutManager(this)
        rvLimitsOverview.layoutManager = layoutManagerLimit
        rvLimitsOverview.itemAnimator = null
        rvLimitsOverview.adapter = limitAdapter
        rvLimitsOverview.setHasFixedSize(true)
    }

    private fun setListeners()
    {
        btnTogglMostUsed.setOnClickListener {
            showCategories = !showCategories
            showApps = !showApps

            showMostUsed()
        }

        btnEditLimits.setOnClickListener {
            startNewIntent("","", LimitActivity::class.java)
        }
    }

    /**
     * Implementation of OnTimeItemClickListener Interface to register Clicks on an entry in the Recycler View for Most Used
     */
    override fun onTimeItemClicked(item: Item)
    {
        startNewIntent(EXTRA_APP_NAME_FOR_DETAILS, item.itemName, AppDetailsActivity::class.java)
    }

    /**
     * Creates a List of all apps that offer usageStats
     */
    private fun getAllApps()
    {
        val usageStatsList: List<UsageStats> = getDailyUsageStats()

        for (useStat in usageStatsList)
        {
            ScreenTimeApp.appInstance.appList.addIfNotContained(
                    AppItem(useStat, this.applicationContext))
        }
    }

    /**
     * Creates a List of all App-Categories
     */
    private fun getAllCategories()
    {
        for(category in AppCategory.values())
        {
            ScreenTimeApp.appInstance.categoryList.addIfNotContained(
                    CategoryItem(category.categoryId, this.applicationContext))
        }
    }

    /**
     * Checks whether one of the apps has exceeded their set limit
     */
    private fun checkLimits()
    {
        for(app in ScreenTimeApp.appInstance.appList.getAppsWithLimit())
        {
            if(longMillisToFloatMinutes(app.useTime) >= app.limit)
            {
                Log.d(TAG, "Use Time ${app.useTime} is higher than Limit: ${app.limit}")
                sendLimitNotification(app as Item)
            }
        }
    }

    /**
     * Toggles between App- and Category View, updates the UsageStats
     */
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

        updateUsageStats()

        appAdapter.update()
        categoryAdapter.update()

        updateOverviewToday()
    }

    /**
     * updates the Lists of Apps and Categories in [ScreenTimeApp] with the current use times and limits
     */
    private fun updateUsageStats()
    {
        val usageStatsList: List<UsageStats> = getDailyUsageStats()

        for(i in usageStatsList.indices)
        {
            val itemFound = ScreenTimeApp.appInstance.appList.updateUsageStats(usageStatsList[i])
        }
        ScreenTimeApp.appInstance.categoryList.updateUsageStats(usageStatsList[0])
    }


    /**
     * Updates the Pie Chart and the Total Use Time today
     */
    private fun updateOverviewToday()
    {
        ScreenTimeApp.appInstance.updateTotalScreenTime()
        tvCurrentDate.text = dayMonthFormat(LocalDate.now())

        chartOverviewToday.notifyDataSetChanged()
    }

    /**
     * gets the usage stats of the current day from the UsageStatsManager
     */
    private fun getDailyUsageStats(): List<UsageStats>
    {
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1)

        val usageStatsManager: UsageStatsManager = getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager

        return usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                                                 calendar.timeInMillis,
                                                 System.currentTimeMillis()
        )
    }

    /**
     * Sends out a notification to the user that the limit for a specific app has been exceeded.
     */
    private fun sendLimitNotification(item: Item)
    {
        val notification: Notification = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.drawable.ic_android)
            .setContentTitle("Limit reached!")
            .setContentText("You have reached the Limit of ${item.limit} Minutes for ${item.itemName}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_SYSTEM)
            .build()

        notificationManager.notify(1, notification)
    }

    /**
     * Starts the provided Activity with the provided message
     */
    private fun startNewIntent(tag: String, message: String, newActivity: Class<*>?){
        Intent(this, newActivity).also {
            it.putExtra(tag, message)
            startActivity(it)
        }
    }
}

