package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.Calendar

interface CalendarRepositories {
    suspend fun getCalendarInfo(): List<Calendar>
}