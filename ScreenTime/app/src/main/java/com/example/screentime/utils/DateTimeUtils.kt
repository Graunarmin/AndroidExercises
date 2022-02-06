package com.example.screentime.utils

import java.time.LocalDate
import java.util.concurrent.TimeUnit

// https://www.baeldung.com/kotlin/dates

fun dayMonthFormat(date: LocalDate) : String
{
    return "Today, " +
           "${date.month.toString()
                    .lowercase()
                    .replaceFirstChar ( Char::uppercase )} " +
           "${date.dayOfMonth}th "
}

fun formatUsageTime(time: Long): String
{
    return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(time),
                         TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1),
                         TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1))
}

fun hourMinFormat(time: Long) : String
{
    return String.format("%dh %02dm", TimeUnit.MILLISECONDS.toHours(time),
                         TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1))
}

fun longMillisToFloatMinutes(time: Long) : Float
{
    return TimeUnit.MILLISECONDS.toMinutes(time).toFloat()
}