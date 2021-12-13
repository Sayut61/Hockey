package com.sayut61.hockey.ui.favorites.tabfragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.domain.entities.PlayerStatisticsInfo
import com.sayut61.hockey.domain.flow.LoadingResult
import com.sayut61.hockey.domain.usecases.PlayersUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersFavoriteViewModel @Inject constructor(
    val playersUseCases: PlayersUseCases
) : ViewModel() {

    private val _playersFavoriteLiveData = MutableLiveData<List<PlayerStatisticsInfo>>()
    val playersFavoriteLiveData: MutableLiveData<List<PlayerStatisticsInfo>> =
        _playersFavoriteLiveData

    private val _errorLiveData = MutableLiveData<java.lang.Exception>()
    val errorLiveData: LiveData<java.lang.Exception> = _errorLiveData

    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData

    fun deleteFromFavorite(playerId: Int) {
        viewModelScope.launch {
            playersUseCases.removeFromFavoritePlayer(playerId)
            refreshFavoriteFragment()
        }
    }

    fun refreshFavoriteFragment() {
        viewModelScope.launch {
            playersUseCases.getPlayersListDB().collect {
                when (it) {
                    is LoadingResult.SuccessResult -> _playersFavoriteLiveData.value = it.data!!
                    is LoadingResult.ErrorResult -> _errorLiveData.value = it.error
                    is LoadingResult.Loading -> _progressBarLiveData.value = it.isLoading
                }
            }
        }
    }
}