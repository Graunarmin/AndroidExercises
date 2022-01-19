 package com.example.screentime

import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

import android.app.AppOpsManager.MODE_ALLOWED
 import android.app.AppOpsManager.OPSTR_GET_USAGE_STATS
import android.content.Intent
import android.os.Process.myUid
import android.provider.Settings
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

 class MainActivity : AppCompatActivity() {

    //private val STORAGE_PERMISSION_CODE = 1
     private lateinit var itemList: ArrayList<ExampleItem>

    private lateinit var btnPermission : Button
    private lateinit var tvUsageStats: TextView
    //private lateinit var rvUsageList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMembers()
        setOnClickListeners()
        //setAdapter()
    }

     private fun initMembers()
     {
         btnPermission = findViewById(R.id.btnPermission)
         tvUsageStats = findViewById(R.id.tvUsageStats)
         //rvUsageList = findViewById(R.id.rvUsageList)
     }

     private fun setAdapter()
     {
         //var adapter = RecyclerAdapter(itemList)
        // var layoutManager = LinearLayoutManager(this)
         //rvUsageList.layoutManager = layoutManager
         //rvUsageList.itemAnimator = DefaultItemAnimator()
        // rvUsageList.adapter = adapter
        // rvUsageList.setHasFixedSize(true)
     }

     private fun setOnClickListeners()
     {
         btnPermission.setOnClickListener {
             onButtonClick(it)
         }
     }

     private fun onButtonClick(view: View)
     {
         if(checkForPermission())
         {
             showUsageStats()
         }
         else
         {
             startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
         }
     }

    private fun checkForPermission() : Boolean {
        val appOpsManager = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOpsManager.checkOpNoThrow(OPSTR_GET_USAGE_STATS, myUid(), packageName)
        return mode == MODE_ALLOWED
    }

    private fun showUsageStats()
    {
        var usageStatsManager: UsageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        var cal: Calendar = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_MONTH, -1)
        var queryUsageStats : List<UsageStats> = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
            cal.timeInMillis,
            System.currentTimeMillis())

        var statsData : String = ""
        var appName: String = ""
        var appTime: String = ""
        for (i in queryUsageStats.indices)
        {
            statsData = statsData + "Package Name: " + queryUsageStats[i].packageName + "\n" +
                    "Last Time Used: " + convertTime(queryUsageStats[i].lastTimeUsed) + "\n" +
                    "Describe Contents: " + queryUsageStats[i].describeContents() + "\n" +
                    "First Time Stamp: " + convertTime(queryUsageStats[i].firstTimeStamp) + "\n" +
                    "Last Time Stamp: " + convertTime(queryUsageStats[i].lastTimeStamp) + "\n" +
                    "Total Time in Foreground: " + convertTime(queryUsageStats[i].totalTimeInForeground) + "\n"

            //appName = queryUsageStats[i].packageName
            //appTime = convertTime(queryUsageStats[i].totalTimeInForeground)

            //setAppInfo(appName, appTime)
        }
        tvUsageStats.text = statsData
    }

     private fun convertTime(lastTimeUsed: Long): String
     {
         var date: Date = Date(lastTimeUsed)
         var format: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
         return format.format(date)
     }

     private fun setAppInfo(appName: String, appTime: String)
     {
         itemList.add(ExampleItem(R.drawable.ic_android, appName, appTime))
     }
 }