package com.example.screentime.utils

import android.util.Log
import java.time.LocalDate
import java.util.concurrent.TimeUnit

// https://www.baeldung.com/kotlin/dates

data class ReadableTime(var millisTime: Long)
{
    val hours : Long = TimeUnit.MILLISECONDS.toHours(millisTime)
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(millisTime) % TimeUnit.HOURS.toMinutes(1)
    val seconds : Long = TimeUnit.MILLISECONDS.toSeconds(millisTime) % TimeUnit.MINUTES.toSeconds(1)
}


fun dayMonthFormat(date: LocalDate) : String
{
    return "Today, " +
           "${date.month.toString()
                    .lowercase()
                    .replaceFirstChar ( Char::uppercase )} " +
           "${date.dayOfMonth}th "
}

fun formatUsageTime(millisTime: Long): String
{
    val time = ReadableTime(millisTime)

    if(time.hours == 0L)
    {
        if(time.minutes == 0L)
        {
            return "< 1 min"
        }
        return String.format("%dmin", time.minutes)
    }
    return String.format("%d:%02dh", time.hours, time.minutes)
}

fun beautifulTime(millisTime: Long) : String
{
    val time = ReadableTime(millisTime)

    if(time.hours == 0L)
    {
        return String.format("%d min %02d sec", time.minutes, time.seconds)
    }
    return String.format("%d h %02d min %02 sec", time.hours, time.minutes, time.seconds)
}

fun withoutSeconds(millisTime: Long) : String
{
    val time = ReadableTime(millisTime)

    if(time.hours == 0L)
    {
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