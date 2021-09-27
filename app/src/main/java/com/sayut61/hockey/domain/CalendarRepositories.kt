package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.Calendar
import java.time.LocalDate

interface CalendarRepositories {
    suspend fun getCalendarInfo(date: LocalDate): List<Calendar>
    suspend fun addToFavorite(calendar: Calendar)
    suspend fun removeFromFavorite(calendar: Calendar)
}