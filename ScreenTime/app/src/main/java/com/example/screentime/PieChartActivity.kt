package com.example.screentime

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.screentime.charts.PieChartCreator
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class PieChartActivity : AppCompatActivity()
{
    private lateinit var piechartView: PieChart

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pie_chart)

        initMembers()
        setUpPieChart()
        loadChartData()
    }

    private fun initMembers()
    {
        piechartView = findViewById(R.id.piechartView)
    }

    private fun setUpPieChart()
    {
        piechartView.isDrawHoleEnabled = true
        piechartView.setUsePercentValues(true)
        piechartView.setEntryLabelTextSize(12f)
        piechartView.setEntryLabelColor(Color.BLACK)
        piechartView.centerText = "Category Times"
        piechartView.setCenterTextSize(24f)
        piechartView.description.isEnabled = false

        val legend: Legend = piechartView.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL //orientation of the labels:
        legend.setDrawInside(false)
        legend.isEnabled = true

    }

    private fun loadChartData()
    {
        val entries: ArrayList<PieEntry> = ArrayList()

        entries.add(PieEntry(20f, "Social Media"))
        entries.add(PieEntry(50f, "Games"))
        entries.add(PieEntry(90f, "Music"))
        entries.add(PieEntry(10f, "Productivity"))
        entries.add(PieEntry(5f, "Reading"))

        val colors = loadColors()

        val data: PieData = PieData(createDataSet(entries, "Category Times", colors))
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(piechartView))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)

        piechartView.data = data
        piechartView.invalidate()
    }

    private fun loadColors() : ArrayList<Int>
    {
        val colors: ArrayList<Int> = ArrayList()

        for(color: Int in ColorTemplate.MATERIAL_COLORS)
        {
            colors.add(color)
        }

        for(color: Int in ColorTemplate.VORDIPLOM_COLORS)
        {
            colors.add(color)
        }
        return colors
    }

    private fun createDataSet(data: ArrayList<PieEntry>, label: String, colors: ArrayList<Int>) : PieDataSet
    {
        val dataSet: PieDataSet = PieDataSet(data, label)
        dataSet.colors = colors

        return dataSet
    }
}