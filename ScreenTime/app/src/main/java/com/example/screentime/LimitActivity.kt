package com.example.screentime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ExpandableListView
import com.example.screentime.adapter.ExpandableListAdapterLimitChoices
import com.example.screentime.categories.AppCategory

class LimitActivity : AppCompatActivity()
{

    private lateinit var explvLimitCategories: ExpandableListView
    private lateinit var btnContinue: Button
    private lateinit var btnCancel: Button

    private lateinit var listViewAdapter : ExpandableListAdapterLimitChoices

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_limit)

        initMembers()
        setListeners()
        setAdapter()

    }

    private fun initMembers()
    {
        explvLimitCategories = findViewById(R.id.explvLimitCategories)
        btnCancel = findViewById(R.id.btnCancel)
        btnContinue = findViewById(R.id.btnContinue)
    }

    private fun setListeners(){

    }

    private fun setAdapter()
    {

        listViewAdapter = ExpandableListAdapterLimitChoices(this.applicationContext,
                                                            ScreenTimeApp.appInstance.categoriesList)

        explvLimitCategories.setAdapter(listViewAdapter)

    }
}