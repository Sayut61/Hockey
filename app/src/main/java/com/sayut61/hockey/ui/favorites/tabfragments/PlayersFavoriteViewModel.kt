package com.sayut61.hockey.ui.favorites.tabfragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.PlayerStatisticsInfo
import com.sayut61.hockey.domain.usecases.PlayersUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersFavoriteViewModel @Inject constructor(
val playersUseCases: PlayersUseCases
): ViewModel() {

    private val _playersFavoriteLiveData = MutableLiveData<List<PlayerStatisticsInfo>>()
    val playersFavoriteLiveData: MutableLiveData<List<PlayerStatisticsInfo>> = _playersFavoriteLiveData

    private val _errorLiveData = MutableLiveData<java.lang.Exception>()
    val errorLiveData: LiveData<java.lang.Exception> = _errorLiveData

    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData

    fun refreshFavoriteFragment(){
        viewModelScope.launch {
            _progressBarLiveData.value = true
            try {
                _playersFavoriteLiveData.value = playersUseCases.getPlayersListDB()
            }catch (ex: Exception){
                _errorLiveData.value = ex
            }
            _progressBarLiveData.value = false
        }
    }
}