package com.example.screentime.charts

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.screentime.ScreenTimeApp
import com.example.screentime.items.CategoryItem
import com.example.screentime.utils.longMillisToFloatMinutes
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

// https://www.programmersought.com/article/17146031876/
// https://www.programmersought.com/article/99524092993/

class PieChartCreator()
{
    private var colors: ArrayList<Int> = ArrayList()
    private var legendLabels: ArrayList<LegendEntry> = ArrayList()

    public fun createPieChart(pieChart: PieChart, centerText: String, context: Context)
    {
        setUpPieChart(pieChart, centerText)
        setUpLegend(pieChart)

        val entries : ArrayList<PieEntry> = getData(context)

        val data : PieData = PieData(createDataSet(entries, colors, "Screen Time Today"))
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)

        pieChart.legend.setCustom(legendLabels)

        pieChart.data = data
        pieChart.invalidate()
    }
    private fun setUpPieChart(pieChart: PieChart, centerText: String)
    {
        pieChart.isDrawHoleEnabled = true
        pieChart.centerText = centerText
        pieChart.setCenterTextSize(18f)

        pieChart.setUsePercentValues(true)
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)

        pieChart.description.isEnabled = false
    }

    private fun setUpLegend(pieChart: PieChart)
    {
        val legend: Legend = pieChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.xOffset = 10f;  //Offset of legend x

        legend.form = Legend.LegendForm.CIRCLE
        legend.setDrawInside(false)
        legend.isWordWrapEnabled = true
        legend.isEnabled = true
    }

    private fun getData(context: Context): ArrayList<PieEntry>
    {
        val entries : ArrayList<PieEntry> = ArrayList()

        for (categoryItem : CategoryItem in ScreenTimeApp.appInstance.categoryTimesList)
        {
            val entry = PieEntry(longMillisToFloatMinutes(categoryItem.useTime), categoryItem.name)
            entry.label = categoryItem.readableUseTime
            entries.add(entry)
            colors.add(ContextCompat.getColor(context, categoryItem.category.color))
        }
        return entries
    }


    private fun createDataSet(data: ArrayList<PieEntry>, colors: ArrayList<Int>, label: String) : PieDataSet
    {
        val dataSet: PieDataSet = PieDataSet(data, label)
        dataSet.colors = colors

        //dataSet.valueLinePart1OffsetPercentage = 80f;

        //Set the color of the connecting line
        //dataSet.valueLineColor = Color.LTGRAY;
        // The connecting line is outside the pie chart
        dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE;
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE;

        dataSet.setDrawValues(false)


        return dataSet
    }
}