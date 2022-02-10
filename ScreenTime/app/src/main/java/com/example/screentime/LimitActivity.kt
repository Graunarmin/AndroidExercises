package com.example.screentime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListView
import com.example.screentime.adapter.ExpandableListAdapterLimitChoices
import com.example.screentime.categories.CategoriesMap
import com.example.screentime.dialogs.DialogLimitPicker

/**
 * Activity that provides an Expandable ListView of all Categories with their respective Apps as children.
 * When the User clicks on one of the Apps, a Dialog for picking a limit is opened.
 */

private const val TAG = "com.example.screentime.limitactivity"
class LimitActivity : AppCompatActivity(), DialogLimitPicker.DialogLimitPickerListener
{

    private lateinit var expandableLVLimitCategories: ExpandableListView

    private lateinit var listViewAdapter : ExpandableListAdapterLimitChoices

    private var setLimitFor: String = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_limit)

        ScreenTimeApp.appInstance.categoriesMap = CategoriesMap()

        initMembers()
        setListeners()
        setAdapter()
    }

    private fun initMembers()
    {
        expandableLVLimitCategories = findViewById(R.id.expandableLVLimitCategories)
    }

    private fun setListeners()
    {
        expandableLVLimitCategories.setOnChildClickListener {
            parent, view, groupPosition, childPosition, id ->
            openDialog(parent.expandableListAdapter.
                getChild(groupPosition, childPosition).toString())
        }
    }

    private fun setAdapter()
    {
        listViewAdapter = ExpandableListAdapterLimitChoices(this.applicationContext,
                                                            ScreenTimeApp.appInstance.categoriesMap)

        expandableLVLimitCategories.setAdapter(listViewAdapter)

    }

    private fun openDialog(appName: String) : Boolean
    {
        setLimitFor = appName
        val limitPickerDialog: DialogLimitPicker = DialogLimitPicker()
        val bundle: Bundle = Bundle()
        bundle.putInt("Limit", ScreenTimeApp.appInstance.appList.getItemLimit(setLimitFor))
        limitPickerDialog.arguments = bundle
        limitPickerDialog.show(supportFragmentManager, "Set Limit for $appName")

        return true
    }

    override fun applyLimit(limit: Int)
    {
        val appItem = ScreenTimeApp.appInstance.appList.getItemByName(setLimitFor)
        appItem?.limit = limit
    }
}