package com.sayut61.hockey.ui.favorites.tabfragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.domain.flow.LoadingResult
import com.sayut61.hockey.domain.usecases.GamesFavUseCases
import com.sayut61.hockey.domain.usecases.GamesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class GamesFavoriteFragmentViewModel @Inject constructor(
    private val gamesFavUseCases: GamesFavUseCases,
) : ViewModel() {
    private val _gamesFavoriteLiveData = MutableLiveData<List<GameFullInfo>>()
    val gamesFavoriteLiveData: MutableLiveData<List<GameFullInfo>> = _gamesFavoriteLiveData
    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData
    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData
    fun deleteFromFavorite(gameGeneralInfo: GameGeneralInfo) {
        viewModelScope.launch {
            gamesFavUseCases.removeFromFavoriteGame(gameGeneralInfo)
        }
        refreshFavoriteFragment()
    }

    fun refreshFavoriteFragment() {
        viewModelScope.launch {
            gamesFavUseCases.getFavoriteGames().collect {
                when (it) {
                    is LoadingResult.SuccessResult -> _gamesFavoriteLiveData.value = it.data!!
                    is LoadingResult.ErrorResult -> _errorLiveData.value = it.error
                    is LoadingResult.Loading -> _progressBarLiveData.value = it.isLoading
                }
            }
        }
    }
}