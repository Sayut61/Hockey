package com.sayut61.hockey.ui.calendar.calendardetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.domain.flow.LoadingResult
import com.sayut61.hockey.domain.usecases.GamesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CalendarDetailViewModel @Inject constructor(
    val gamesUseCases: GamesUseCases
) : ViewModel() {

    private val _getGameInfo = MutableLiveData<GameFullInfo>()
    val getGameInfo: LiveData<GameFullInfo> = _getGameInfo

    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData

    fun refreshViewModel(gameGeneralInfo: GameGeneralInfo){
        viewModelScope.launch {
                gamesUseCases.getGameFullInfo(gameGeneralInfo).collect {
                    when(it){
                        is LoadingResult.SuccessResult->{
                            _getGameInfo.value = it.data!!
                        }
                        is LoadingResult.ErrorResult->{
                            _errorLiveData.value = it.error
                        }
                        is LoadingResult.Loading->{
                            _progressBarLiveData.value = it.isLoading
                        }
                    }
                }

        }
    }
}