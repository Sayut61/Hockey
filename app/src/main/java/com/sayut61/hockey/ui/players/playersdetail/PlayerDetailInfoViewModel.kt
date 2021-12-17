package com.sayut61.hockey.ui.players.playersdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.PlayerFullInfo
import com.sayut61.hockey.domain.entities.PlayerStatisticsInfo
import com.sayut61.hockey.domain.usecases.PlayersUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerDetailInfoViewModel @Inject constructor(
    val playersUseCases: PlayersUseCases
) : ViewModel() {
    private val _playerLiveData = MutableLiveData<PlayerFullInfo>()
    val playerLiveData: LiveData<PlayerFullInfo> = _playerLiveData

    private val _playerStatisticLiveData = MutableLiveData<PlayerStatisticsInfo>()
    val playerStatisticLiveData: LiveData<PlayerStatisticsInfo> = _playerStatisticLiveData

    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData

    fun refreshPlayerDetail(playerId: Int) {
        viewModelScope.launch {
            _progressBarLiveData.value = true
            try {
                val playerFullInfo = playersUseCases.getPlayerFullInfo(playerId)
                _playerLiveData.value = playerFullInfo
                _playerStatisticLiveData.value = playersUseCases.getPlayerStatistic(playerFullInfo)
            } catch (ex: Exception) {
                _errorLiveData.value = ex
            }
            _progressBarLiveData.value = false
        }
    }
}