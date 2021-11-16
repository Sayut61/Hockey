package com.sayut61.hockey.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import com.sayut61.hockey.ui.adapters.CalendarDay
import java.time.LocalDate

class MyCalendar {
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
    fun getCurrentDay(): LocalDate{
        return LocalDate.of(year, month, day)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentMonthName(): String{
        val localDate = LocalDate.of(year, month, 1)
        val monthName = when(localDate.month.name){
            "JANUARY"-> "ЯНВАРЬ"
            "FEBRUARY"-> "ФЕВРАЛЬ"
            "MARCH"-> "МАРТ"
            "APRIL"-> "АПРЕЛЬ"
            "MAY"-> "МАЙ"
            "JUNE"-> "ИЮНЬ"
            "JULY"-> "ИЮЛЬ"
            "AUGUST"-> "АВГУСТ"
            "SEPTEMBER"-> "СЕНТЯБРЬ"
            "OCTOBER"-> "ОКТЯБРЬ"
            "NOVEMBER"-> "НОЯБРЬ"
            "DECEMBER"-> "ДЕКАБРЬ"
            else -> ""
        }
        return monthName
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addMonth(){
        if(month in 1..11){
            month++
        } else {
            month = 1
            year++
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun reduceMonth(){
        if(month > 1)
            month--
        else {
            month = 12
            year--
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDaysList(): List<CalendarDay>{
        val daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth()
        val daysList = mutableListOf<CalendarDay>()
        for (dayNumber in 1..daysInMonth){
            val localDate = LocalDate.of(year, month, dayNumber)
            var dayOfWeek = when(localDate.dayOfWeek.name) {
                "MONDAY"-> "пн"
                "TUESDAY"-> "вт"
                "WEDNESDAY"-> "ср"
                "THURSDAY"-> "чт"
                "FRIDAY"-> "пт"
                "SATURDAY"-> "сб"
                "SUNDAY"-> "вс"
                else -> ""
            }
            val calendarDay = CalendarDay(dayNumber, dayOfWeek , month, year)
            daysList.add(calendarDay)
        }
        return daysList
    }
}