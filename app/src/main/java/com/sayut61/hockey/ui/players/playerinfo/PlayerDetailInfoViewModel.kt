package com.sayut61.hockey.ui.players.playerinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.PlayerFullInfo
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.domain.usecases.PlayersUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
@HiltViewModel
class PlayerDetailInfoViewModel @Inject constructor(
    val playersUseCases: PlayersUseCases
) : ViewModel() {
    private val _playerLiveData = MutableLiveData<PlayerFullInfo>()
    val playerLiveData: LiveData<PlayerFullInfo> = _playerLiveData

    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData

    fun refreshPlayerDetail(playerId: Int){
        viewModelScope.launch {
            _progressBarLiveData.value = true
            try {
                _playerLiveData.value = playersUseCases.getPlayerFullInfo(playerId)
            }catch (ex: Exception){
                _errorLiveData.value = ex
            }
            _progressBarLiveData.value = false
        }
    }
}