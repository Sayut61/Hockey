package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.CalendarRepositories
import com.sayut61.hockey.domain.entities.Calendar
import java.time.LocalDate
import javax.inject.Inject

class CalendarUseCases @Inject constructor(
    private val calendarRepositories: CalendarRepositories
) {
    suspend fun getCalendarInfo(date: LocalDate): List<Calendar>{
        return calendarRepositories.getCalendarInfo(date)
    }
}