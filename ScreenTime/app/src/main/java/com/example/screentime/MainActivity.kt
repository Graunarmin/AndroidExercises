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

 private const val TAG = "com.example.screentime.MainActivity"
 class MainActivity : AppCompatActivity()
 {
    private lateinit var btnPermission : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMembers()
        setOnClickListeners()
    }

     private fun initMembers()
     {
         btnPermission = findViewById(R.id.btnPermission)

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
             startNewIntent()
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

     private fun startNewIntent(){
         val intent = Intent(this, ScreenTimeHome::class.java)
         startActivity(intent)
     }

 }