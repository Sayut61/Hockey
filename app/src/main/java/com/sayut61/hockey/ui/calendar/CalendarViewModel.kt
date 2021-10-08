package com.sayut61.hockey.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.Game
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
    private val _gamesLiveData = MutableLiveData<List<Game>>()
    val gamesLiveData: LiveData<List<Game>> = _gamesLiveData
    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    var date: LocalDate? = null

    fun changeDate(date: LocalDate) {
        this.date = date
        refreshViewModel(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addGameInDB(game: Game) {
        viewModelScope.launch {
            gamesFavUseCases.addToFavorite(game)
            date?.let {
                refreshViewModel(it)
            }
        }
    }

    fun removeGameInDB(game: Game) {
        viewModelScope.launch {
            gamesFavUseCases.removeFromFavorite(game)
            date?.let {
                refreshViewModel(it)
            }
        }
    }

    private fun refreshViewModel(date: LocalDate) {
        viewModelScope.launch {
            try {
                _gamesLiveData.value = gamesUseCases.getGamesInfo(date)
            } catch (ex: Exception) {
                _errorLiveData.value = ex
            }
        }
    }
}