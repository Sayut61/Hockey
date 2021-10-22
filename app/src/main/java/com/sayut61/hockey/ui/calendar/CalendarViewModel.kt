package com.sayut61.hockey.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.domain.usecases.GamesFavUseCases
import com.sayut61.hockey.domain.usecases.GamesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val gamesUseCases: GamesUseCases,
    private val gamesFavUseCases: GamesFavUseCases
) : ViewModel() {
    private val _gamesLiveData = MutableLiveData<List<GameGeneralInfo>>()
    val gamesLiveData: LiveData<List<GameGeneralInfo>> = _gamesLiveData
    private val _gameFullInfoLiveData = MutableLiveData<List<GameFullInfo>>()
    val gameFullInfoLiveData:LiveData<List<GameFullInfo>> = _gameFullInfoLiveData
    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    var date: LocalDate? = null

    fun changeDate(date: LocalDate) {
        this.date = date
        refreshViewModel(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addGameInDB(gameGeneralInfo: GameGeneralInfo) {
        viewModelScope.launch {
            gamesFavUseCases.addToFavoriteGame(gameGeneralInfo)
            date?.let {
                refreshViewModel(it)
            }
        }
    }

    fun removeGameInDB(gameGeneralInfo: GameGeneralInfo) {
        viewModelScope.launch {
            gamesFavUseCases.removeFromFavoriteGame(gameGeneralInfo)
            date?.let {
                refreshViewModel(it)
            }
        }
    }

    private fun refreshViewModel(date: LocalDate) {
        viewModelScope.launch {
            try {
                val gamesGeneralInfo =  gamesUseCases.getGamesInfo(date)
                _gamesLiveData.value =gamesGeneralInfo
                val gamesFullInfo = gamesGeneralInfo.map { gamesUseCases.getGameFullInfo(it) }
                _gameFullInfoLiveData.value = gamesFullInfo
            } catch (ex: Exception) {
                _errorLiveData.value = ex
            }
        }
    }
}