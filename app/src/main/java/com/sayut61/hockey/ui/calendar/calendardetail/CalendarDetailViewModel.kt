package com.sayut61.hockey.ui.calendar.calendardetail

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

    fun refreshViewModel(gameGeneralInfo: GameGeneralInfo){
        viewModelScope.launch {
            try {
                _getGameInfo.value = gamesUseCases.getGameFullInfo(gameGeneralInfo)
            }catch (ex: Exception){
                _errorLiveData.value = ex
            }
        }
    }
}