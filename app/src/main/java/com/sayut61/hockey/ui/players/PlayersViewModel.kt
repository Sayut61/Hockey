package com.sayut61.hockey.ui.players

import androidx.lifecycle.*
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.domain.flow.LoadingResult
import com.sayut61.hockey.domain.usecases.PlayersUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val playersUseCases: PlayersUseCases
) : ViewModel() {
    private val _listPlayersLiveData = MutableLiveData<List<PlayerGeneralInfo>>()
    var textForFilterLiveData = MutableLiveData<String>()
    val listPlayersLiveData = MediatorLiveData<List<PlayerGeneralInfo>>().apply {
        addSource(_listPlayersLiveData) { value = filterPlayers() }
        addSource(textForFilterLiveData) { value = filterPlayers() }
    }

    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData

    fun changeFilter(text: String) {
        textForFilterLiveData.value = text
    }

    fun onFavoriteClick(playerGeneralInfo: PlayerGeneralInfo) {
        viewModelScope.launch {
            if (playerGeneralInfo.isInFavorite)
                playersUseCases.removeFromFavoritePlayer(playerGeneralInfo.playerId)
            else
                playersUseCases.addToFavoritePlayer(playerGeneralInfo)
            refreshFragment()
        }
    }

    fun refreshFragment() {
        viewModelScope.launch {
            playersUseCases.getPlayersListApi().collect {
                when (it) {
                    is LoadingResult.SuccessResult -> {
                        _listPlayersLiveData.value = it.data!!
                    }
                    is LoadingResult.ErrorResult -> {
                        _errorLiveData.value = it.error
                    }
                    is LoadingResult.Loading -> {
                        _progressBarLiveData.value = it.isLoading
                    }
                }
            }
        }
    }

    private fun filterPlayers(): List<PlayerGeneralInfo>? {
        return _listPlayersLiveData.value?.filter {
            it.fullName.contains(
                textForFilterLiveData.value ?: "", true
            )
        }
    }
}