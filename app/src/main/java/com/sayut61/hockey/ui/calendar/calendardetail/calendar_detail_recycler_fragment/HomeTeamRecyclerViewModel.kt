package com.sayut61.hockey.ui.calendar.calendardetail.calendar_detail_recycler_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.domain.entities.PlayerNameAndNumber
import com.sayut61.hockey.domain.usecases.GamesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeTeamRecyclerViewModel @Inject constructor(
   private val gamesUseCases: GamesUseCases
) : ViewModel() {
    private val _homePlayersLiveData = MutableLiveData<List<PlayerNameAndNumber>>()
    val homePlayersLiveData: LiveData<List<PlayerNameAndNumber>> = _homePlayersLiveData
    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData
    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData
    fun refreshFragment(gameGeneralInfo: GameGeneralInfo) {
        viewModelScope.launch {
            _progressBarLiveData.value = true
            try {
                _homePlayersLiveData.value =
                    gamesUseCases.getGameFullInfo(gameGeneralInfo).playersHomeTeam!!
            } catch (ex: Exception) {
                _errorLiveData.value = ex
            }
            _progressBarLiveData.value = false
        }
    }
}