package com.sayut61.hockey.ui.players

import androidx.lifecycle.*
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.domain.usecases.PlayersUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val playersUseCases: PlayersUseCases
) : ViewModel() {
    private val _listPlayersLiveData = MutableLiveData<List<PlayerGeneralInfo>>()
    var textForFilterLiveData = MutableLiveData<String>()
    val listPlayersLiveData = MediatorLiveData<List<PlayerGeneralInfo>>().apply {
        addSource(_listPlayersLiveData){ value = filterPlayers() }
        addSource(textForFilterLiveData){ value = filterPlayers() }
    }

    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData

    fun changeFilter(text: String){
        textForFilterLiveData.value = text
    }

    fun onFavoriteClick(playerGeneralInfo: PlayerGeneralInfo){
        viewModelScope.launch {
            if (playerGeneralInfo.isInFavorite)
                playersUseCases.removeFromFavoritePlayer(playerGeneralInfo)
            else
                playersUseCases.addToFavoritePlayer(playerGeneralInfo)
            refreshFragment(false)
        }
    }

    fun refreshFragment(showProgressBar: Boolean = true){
        viewModelScope.launch {
            if (showProgressBar)
                _progressBarLiveData.value = true
            try {
                _listPlayersLiveData.value = playersUseCases.getPlayersListApi()
            }catch (ex: Exception){
                _errorLiveData.value = ex
            }
            if (showProgressBar)
                _progressBarLiveData.value = false
        }
    }

    private fun filterPlayers(): List<PlayerGeneralInfo>? {
//        val players = _listPlayersLiveData.value
        return _listPlayersLiveData.value?.filter { it.fullName.contains(textForFilterLiveData.value?:"", true) }
    }
}