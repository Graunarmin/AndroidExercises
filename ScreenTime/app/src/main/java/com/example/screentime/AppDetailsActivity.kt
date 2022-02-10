package com.example.screentime

import android.app.Notification
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.screentime.dialogs.DialogLimitPicker
import com.example.screentime.items.AppItem
import com.example.screentime.items.Item
import com.example.screentime.utils.floatMinutesToTime
import com.github.mikephil.charting.charts.BarChart

/**
 * Activity that provides all details about one app and a dialog the edit the limit of said app
 */

private const val TAG = "com.example.screentime.appdetailsactivity"
class AppDetailsActivity : AppCompatActivity(), DialogLimitPicker.DialogLimitPickerListener
{
    private lateinit var ivAppIcon: ImageView
    private lateinit var tvAppCategory: TextView
    private lateinit var tvAppName: TextView
    private lateinit var tvUseTimeToday: TextView

    //private lateinit var tvDailyAverage: TextView
    //private lateinit var barChart: BarChart

    private lateinit var tvLimitInfo: TextView
    private lateinit var btnAppDetailEditLimit: Button

    private lateinit var appItem: AppItem

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_details)

        val detailsFor = intent.getStringExtra(EXTRA_APP_NAME_FOR_DETAILS).toString()
        appItem = ScreenTimeApp.appInstance.appList.getItemByName(detailsFor) as AppItem

        initMembers()
        setListeners()
        setDetails()
    }

    private fun initMembers()
    {
        ivAppIcon = findViewById(R.id.ivAppIcon)
        tvAppCategory = findViewById(R.id.tvAppCategory)
        tvAppName = findViewById(R.id.tvAppName)
        tvUseTimeToday = findViewById(R.id.tvUseTimeToday)
        //tvDailyAverage = findViewById(R.id.tvDailyAverage)
        //barChart = findViewById(R.id.barChart)
        tvLimitInfo = findViewById(R.id.tvLimitInfo)
        btnAppDetailEditLimit = findViewById(R.id.btnAppDetailEditLimit)
    }

    private fun setListeners()
    {
        btnAppDetailEditLimit.setOnClickListener {
            openDialog(appItem.itemName)
        }
    }

    private fun setDetails()
    {
        ivAppIcon.setImageDrawable(appItem.icon)
        tvAppCategory.text = appItem.category.printableName
        tvAppName.text = appItem.itemName
        tvUseTimeToday.text = appItem.readableUseTime
        updateLimitInfo()
    }

    private fun updateLimitInfo()
    {
        if(appItem.limit != -1)
        {
            tvLimitInfo.text = floatMinutesToTime((appItem.limit).toFloat())
        }else
        {
            tvLimitInfo.text = getString(R.string.no_limit)
        }
    }

    private fun openDialog(appName: String) : Boolean
    {
        val limitPickerDialog: DialogLimitPicker = DialogLimitPicker()
        val bundle: Bundle = Bundle()
        bundle.putInt("Limit", ScreenTimeApp.appInstance.appList.getItemLimit(appName))
        limitPickerDialog.arguments = bundle
        limitPickerDialog.show(supportFragmentManager, "Set Limit for $appName")

        return true
    }

    override fun applyLimit(limit: Int)
    {
        appItem.limit = limit
        updateLimitInfo()
    }
}