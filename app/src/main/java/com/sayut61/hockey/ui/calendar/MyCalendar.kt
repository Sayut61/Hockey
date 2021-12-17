package com.sayut61.hockey.ui.calendar

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.sayut61.hockey.ui.adapters.CalendarDay
import java.time.LocalDate

class MyCalendar(val context: Context) {
    @RequiresApi(Build.VERSION_CODES.O)
    var year = LocalDate.now().year
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    var month = LocalDate.now().monthValue
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    var day = LocalDate.now().dayOfMonth
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDay(): LocalDate {
        return LocalDate.of(year, month, day)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentMonthName(): String {
        val localDate = LocalDate.of(year, month, 1)
        val locale = context.resources.configuration.locales[0]
        return if (locale.language == "ru")
            when (localDate.monthValue) {
                1 -> "ЯНВАРЬ"
                2 -> "ФЕВРАЛЬ"
                3 -> "МАРТ"
                4 -> "АПРЕЛЬ"
                5 -> "МАЙ"
                6 -> "ИЮНЬ"
                7 -> "ИЮЛЬ"
                8 -> "АВГУСТ"
                9 -> "СЕНТЯБРЬ"
                10 -> "ОКТЯБРЬ"
                11 -> "НОЯБРЬ"
                12 -> "ДЕКАБРЬ"
                else -> ""
            }
        else
            localDate.month.name
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addMonth() {
        if (month in 1..11) {
            month++
        } else {
            month = 1
            year++
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun reduceMonth() {
        if (month > 1)
            month--
        else {
            month = 12
            year--
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCalendarDay(): CalendarDay {
        val localDate = LocalDate.of(year, month, day)
        var dayOfWeek = when (localDate.dayOfWeek.name) {
            "MONDAY" -> "пн"
            "TUESDAY" -> "вт"
            "WEDNESDAY" -> "ср"
            "THURSDAY" -> "чт"
            "FRIDAY" -> "пт"
            "SATURDAY" -> "сб"
            "SUNDAY" -> "вс"
            else -> ""
        }
        return CalendarDay(day, dayOfWeek, month, year)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDaysList(): List<CalendarDay> {
        val daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth()
        val daysList = mutableListOf<CalendarDay>()
        for (dayNumber in 1..daysInMonth) {
            val localDate = LocalDate.of(year, month, dayNumber)
            var dayOfWeek = when (localDate.dayOfWeek.name) {
                "MONDAY" -> "пн"
                "TUESDAY" -> "вт"
                "WEDNESDAY" -> "ср"
                "THURSDAY" -> "чт"
                "FRIDAY" -> "пт"
                "SATURDAY" -> "сб"
                "SUNDAY" -> "вс"
                else -> ""
            }
            val calendarDay = CalendarDay(dayNumber, dayOfWeek, month, year)
            daysList.add(calendarDay)
        }
        return daysList
    }
}