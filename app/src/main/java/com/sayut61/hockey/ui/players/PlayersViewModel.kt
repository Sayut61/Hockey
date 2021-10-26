package com.sayut61.hockey.ui.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.Player
import com.sayut61.hockey.domain.usecases.PlayersUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val playersUseCases: PlayersUseCases
) : ViewModel() {
    private val _listPlayersLiveData = MutableLiveData<List<Player>>()
    val listPlayerLiveData: LiveData<List<Player>> = _listPlayersLiveData

    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    fun addToFavorite(){
        viewModelScope.launch {
            val players = _listPlayersLiveData.value
            if (players != null) {
                for (player in players){
                    playersUseCases.addToFavoritePlayer(player)
                }
            }
        }
    }

    fun refreshFragment(){
        viewModelScope.launch {
            try {
                _listPlayersLiveData.value = playersUseCases.getPlayersListApi()
            }catch (ex: Exception){
                _errorLiveData.value = ex
            }
        }
    }
}