package com.sayut61.hockey.ui.favorites.tabfragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.domain.flow.LoadingResult
import com.sayut61.hockey.domain.usecases.GamesFavoriteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesFavoriteViewModel @Inject constructor(
    private val gamesFavoriteUseCases: GamesFavoriteUseCases,
) : ViewModel() {
    private val _gamesFavoriteLiveData = MutableLiveData<List<GameFullInfo>>()
    val gamesFavoriteLiveData: MutableLiveData<List<GameFullInfo>> = _gamesFavoriteLiveData
    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData
    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData
    fun deleteFromFavorite(gameGeneralInfo: GameGeneralInfo) {
        viewModelScope.launch {
            gamesFavoriteUseCases.removeFromFavoriteGame(gameGeneralInfo)
        }
        refreshFavoriteFragment()
    }

    fun refreshFavoriteFragment() {
        viewModelScope.launch {

            _progressBarLiveData.value = true
            try {
                gamesFavoriteUseCases.getFavoriteGames().collect {
                    _gamesFavoriteLiveData.value = it
                }
            }catch (ex: Exception){
                _errorLiveData.value = ex
            }
            _progressBarLiveData.value = false
        }
    }
}