package com.example.screentime.utils

import android.nfc.Tag
import android.util.Log
import java.time.LocalDate
import java.util.concurrent.TimeUnit

private const val TAG = "<DEBUG> com.example.screentime.utils.DateTimeUtils"
// https://www.baeldung.com/kotlin/dates

data class ReadableTime(var millisTime: Long)
{
    val hours : Long = TimeUnit.MILLISECONDS.toHours(millisTime)
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(millisTime) % TimeUnit.HOURS.toMinutes(1)
    val seconds : Long = TimeUnit.MILLISECONDS.toSeconds(millisTime) % TimeUnit.MINUTES.toSeconds(1)
}

/**
 * Formats Date as "Today, Weekday #th
 */
fun dayMonthFormat(date: LocalDate) : String
{
    return "Today, " +
           "${date.month.toString()
                    .lowercase()
                    .replaceFirstChar ( Char::uppercase )} " +
           "${date.dayOfMonth}th "
}

/**
 * Formats Time as #h ##min bzw. ##min bzw. <1min
 */
fun formatUsageTime(millisTime: Long): String
{
    val time = ReadableTime(millisTime)

    if(time.hours == 0L)
    {
        if(time.minutes == 0L)
        {
            return "< 1min"
        }
        return String.format("%dmin", time.minutes)
    }
    return String.format("%dh %02dmin", time.hours, time.minutes)
}

fun floatMinutesToTime(minutes: Float) : String
{
    var hours: Int = 0
    var min: Int = 0
    if(minutes > 60)
    {
        min = (minutes % 60).toInt()
        hours = ((minutes - min) / 60).toInt()
        return String.format("%dh\n%02dmin", hours, min)
    }
    else if (minutes == 60f)
    {
        hours = 1
        return String.format("%dh", hours)
    }
    else
    {
        min = minutes.toInt()
        return String.format("%dmin", min)
    }
}

fun hourMinFormat(millisTime: Long) : String
{
    val time = ReadableTime(millisTime)
    return String.format("%dh %02dm", time.hours, time.minutes)
}

fun longMillisToFloatMinutes(time: Long) : Float
{
    return TimeUnit.MILLISECONDS.toMinutes(time).toFloat()
}