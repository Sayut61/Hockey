package com.sayut61.hockey.ui.favorites.tabfragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.Game
import com.sayut61.hockey.domain.usecases.GamesFavUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class GamesFavoriteFragmentViewModel @Inject constructor(
    private val gamesFavUseCases: GamesFavUseCases
) : ViewModel() {
    private val _gamesFavoriteLiveData = MutableLiveData<List<Game>>()
    val gamesFavoriteLiveData: MutableLiveData<List<Game>> = _gamesFavoriteLiveData

    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData


    fun deleteFromFavorite(game: Game) {
        viewModelScope.launch {
            gamesFavUseCases.removeFromFavorite(game)
        }
        refreshFavoriteFragment()
    }

    fun refreshFavoriteFragment() {
        viewModelScope.launch {
            try {
                _gamesFavoriteLiveData.value = gamesFavUseCases.getGames()
            } catch (ex: Exception) {
                _errorLiveData.value = ex
            }
        }
    }
}