package com.sayut61.hockey.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
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
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarUseCases: CalendarUseCases
) : ViewModel() {
    private val _calendarLiveData = MutableLiveData<List<Calendar>>()
    val calendarLiveData: LiveData<List<Calendar>> = _calendarLiveData
    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    var date: LocalDate? = null

    fun changeDate(date: LocalDate) {
        this.date = date
        refreshViewModel(date)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun addGameInDB(calendar: Calendar){
        viewModelScope.launch {
            calendarUseCases.addToFavorite(calendar)
/*            val dateValue = date
            if(dateValue != null)
                refreshViewModel(dateValue)*/
            date?.let {
                refreshViewModel(it)
            }
        }
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