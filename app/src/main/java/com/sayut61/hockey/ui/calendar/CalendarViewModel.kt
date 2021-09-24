package com.sayut61.hockey.ui.calendar

import androidx.lifecycle.ViewModel
import com.sayut61.hockey.domain.usecases.CalendarUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarUseCases: CalendarUseCases
): ViewModel() {

}