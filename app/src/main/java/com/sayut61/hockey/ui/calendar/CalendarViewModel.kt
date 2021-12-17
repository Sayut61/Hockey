package com.sayut61.hockey.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.domain.usecases.GamesFavoriteUseCases
import com.sayut61.hockey.domain.usecases.GamesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val gamesUseCases: GamesUseCases,
    private val gamesFavoriteUseCases: GamesFavoriteUseCases
) : ViewModel() {
    private val _gamesLiveData = MutableLiveData<List<GameFullInfo>>()
    val gamesLiveData: LiveData<List<GameFullInfo>> = _gamesLiveData
    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData
    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData
    var date: LocalDate? = null

    fun changeDate(date: LocalDate) {
        this.date = date
        refreshViewModel(date)
    }

    fun onFavoriteClick(gameGeneralInfo: GameGeneralInfo) {
        viewModelScope.launch {
            if (gameGeneralInfo.isInFavoriteGame) {
                gamesFavoriteUseCases.removeFromFavoriteGame(gameGeneralInfo)
                date?.let { refreshViewModel(it, false) }
            } else {
                gamesFavoriteUseCases.addToFavoriteGame(gameGeneralInfo)
                date?.let { refreshViewModel(it, false) }
            }
        }
    }

    private fun refreshViewModel(date: LocalDate, showProgressBar: Boolean = true) {
        viewModelScope.launch {
            _progressBarLiveData.value = true
            try {
                gamesUseCases.getGamesInfo(date).collect { gamesFullInfo ->
                    _gamesLiveData.value = gamesFullInfo
                }
            } catch (e: Exception) {
                _errorLiveData.value = e
            }
            _progressBarLiveData.value = false
        }
    }
}