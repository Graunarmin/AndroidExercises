package com.example.screentime.charts

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.screentime.ScreenTimeApp
import com.example.screentime.items.Item
import com.example.screentime.utils.*
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

// https://www.programmersought.com/article/17146031876/
// https://www.programmersought.com/article/99524092993/

/**
 * Creates a PieChart of the use time of the different categories.
 */

private const val TAG = "com.example.screentime.charts.PieChartCreator"
class PieChartCreator()
{
    private var colors: ArrayList<Int> = ArrayList()

    public fun createPieChart(pieChart: PieChart, centerText: String, context: Context)
    {
        setUpPieChart(pieChart, centerText)
        setUpLegend(pieChart)

        val entries : ArrayList<PieEntry> = getData(context)

        val data : PieData = PieData(createDataSet(entries, colors))

        pieChart.rotationAngle = 45f

        pieChart.data = data
        pieChart.invalidate()
    }

    private fun setUpPieChart(pieChart: PieChart, centerText: String)
    {
        // Donut
        pieChart.isDrawHoleEnabled = true
        pieChart.holeRadius = (pieChart.holeRadius) * 1f

        // Text in the Center
        pieChart.centerText = centerText
        pieChart.setCenterTextSize(18f)

        // Labels
        pieChart.setDrawEntryLabels(false);
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)

        // Values
        pieChart.setUsePercentValues(false)

        pieChart.description.isEnabled = false
    }

    private fun setUpLegend(pieChart: PieChart)
    {
        val legend: Legend = pieChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        //legend.xOffset = 10f;  //Offset of legend x

        legend.textSize = 12f

        legend.form = Legend.LegendForm.CIRCLE
        legend.setDrawInside(false)
        legend.isWordWrapEnabled = true
        legend.isEnabled = true
    }

    private fun getData(context: Context): ArrayList<PieEntry>
    {
        val entries : ArrayList<PieEntry> = ArrayList()

        for (categoryItem : Item in ScreenTimeApp.appInstance.categoryList.getUsedCategories())
        {
            val entry = PieEntry(longMillisToFloatMinutes(categoryItem.useTime), categoryItem.itemName)
            //exclude entries that are too smalls
            if (entry.value < 2f)
            {
                break
            }
            entries.add(entry)
            colors.add(ContextCompat.getColor(context, categoryItem.category.color))
        }
        return entries
    }


    private fun createDataSet(data: ArrayList<PieEntry>, colors: ArrayList<Int>) : PieDataSet
    {
        val dataSet: PieDataSet = PieDataSet(data, "")
        dataSet.colors = colors

        // Connecting line
        dataSet.valueLinePart1OffsetPercentage = 80f;
        dataSet.valueLineColor = Color.LTGRAY;

        //Labels outside
        dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE;

        //Values outside
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE;

        //Value Styling
        dataSet.setDrawValues(true)
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.BLACK

        dataSet.valueFormatter = TimeFormatter()

        return dataSet
    }
}