package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.CalendarRepositories
import com.sayut61.hockey.domain.entities.Calendar
import javax.inject.Inject

class CalendarUseCases @Inject constructor(
    private val calendarRepositories: CalendarRepositories
) {
    suspend fun getCalendarInfo(): List<Calendar>{
        return calendarRepositories.getCalendarInfo()
    }
}