package com.sayut61.hockey.ui.calendar

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
import kotlinx.coroutines.flow.map
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
    val gameFullInfoLiveData: LiveData<List<GameFullInfo>> = _gameFullInfoLiveData
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
                gamesFavUseCases.removeFromFavoriteGame(gameGeneralInfo)
                date?.let { refreshViewModel(it, false) }
            } else {
                gamesFavUseCases.addToFavoriteGame(gameGeneralInfo)
                date?.let { refreshViewModel(it, false) }
            }
        }
    }
    private fun refreshViewModel(date: LocalDate, showProgressBar: Boolean = true) {
        viewModelScope.launch {

            val gamesGeneralInfo = gamesUseCases.getGamesInfo(date)
            val gameFullInfo = gamesGeneralInfo.map { gamesUseCases.getGameFullInfo(it) }

            gamesGeneralInfo.collect {
                when (it) {
                    is LoadingResult.SuccessResult -> {
                        _gamesLiveData.value = it.data!!
                    }
                    is LoadingResult.ErrorResult -> {
                        _errorLiveData.value = it.error
                    }
                    is LoadingResult.Loading -> {
                        _progressBarLiveData.value = it.isLoading
                    }
                }
            }
            gameFullInfo.collect {
                when(it){
                    is LoadingResult.SuccessResult ->{
                        _gameFullInfoLiveData.value = it.data!!
                    }
                    is LoadingResult.ErrorResult ->{
                        _errorLiveData.value = it.error
                    }
                    is LoadingResult.Loading ->{
                        _progressBarLiveData.value = it.isLoading
                    }
                }
            }
        }
    }
}