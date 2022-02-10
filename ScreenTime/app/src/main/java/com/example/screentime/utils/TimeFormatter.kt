package com.example.screentime.utils

import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat

/**
 * Formatter for the PieChartCreator
 */
class TimeFormatter() : ValueFormatter()
{

    override fun getFormattedValue(value: Float): String
    {
        return floatMinutesToTime(value)
    }

    override fun getPieLabel(value: Float, pieEntry: PieEntry?): String
    {
        return getFormattedValue(value)
    }
}