package com.sayut61.hockey.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.Calendar
import com.sayut61.hockey.domain.usecases.CalendarUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarUseCases: CalendarUseCases
) : ViewModel() {

    private val _calendarLiveData = MutableLiveData<List<Calendar>>()
    val calendarLiveData: LiveData<List<Calendar>> = _calendarLiveData

    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    fun changeDate(date: LocalDate) {
        refreshViewModel(date)
    }

    private fun refreshViewModel(date: LocalDate) {
        viewModelScope.launch {
            try {
                _calendarLiveData.value = calendarUseCases.getCalendarInfo(date)
            } catch (ex: Exception) {
                _errorLiveData.value = ex
            }
        }
    }
}