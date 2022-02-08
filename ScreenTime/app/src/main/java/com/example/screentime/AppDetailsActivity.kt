package com.example.screentime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

private const val TAG = "<-!-DEBUG-!-> com.example.screentime.appdetailsactivity"
class AppDetailsActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_details)
    }
}