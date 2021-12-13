package com.sayut61.hockey.ui.calendar.calendardetail.adapters_and_recyclerFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.domain.entities.PlayerNameAndNumber
import com.sayut61.hockey.domain.usecases.GamesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AwayTeamRecyclerViewModel @Inject constructor(
    val gamesUseCases: GamesUseCases
): ViewModel() {
    private val _awayPlayersLiveData = MutableLiveData<List<PlayerNameAndNumber>>()
    val  awayPlayersLiveData: LiveData<List<PlayerNameAndNumber>> = _awayPlayersLiveData
    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData
    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData
    fun refreshFragment(gameGeneralInfo: GameGeneralInfo){
        viewModelScope.launch {
            _progressBarLiveData.value = true
            try {
                _awayPlayersLiveData.value = gamesUseCases.getGameFullInfo(gameGeneralInfo).playersAwayTeam!!
            }catch (ex: Exception){
                _errorLiveData.value = ex
            }
            _progressBarLiveData.value = false
        }
    }
}