package com.sayut61.hockey.ui.calendar.calendardetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.domain.usecases.GamesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CalendarDetailViewModel @Inject constructor(
    val gamesUseCases: GamesUseCases
) : ViewModel() {

    private val _getGameInfo = MutableLiveData<GameFullInfo>()
    val getGameInfo: LiveData<GameFullInfo> = _getGameInfo

    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData

    fun refreshViewModel(gameGeneralInfo: GameGeneralInfo){
        viewModelScope.launch {
            _progressBarLiveData.value = true
            try {
                val result = gamesUseCases.getGameFullInfo(gameGeneralInfo)
                _getGameInfo.value = result
            }catch (ex: Exception){
                _errorLiveData.value = ex
            }
            _progressBarLiveData.value = false
        }
    }
}