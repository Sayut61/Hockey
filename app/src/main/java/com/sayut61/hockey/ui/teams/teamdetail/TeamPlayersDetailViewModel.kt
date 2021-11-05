package com.sayut61.hockey.ui.teams.teamdetail

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.TeamFullInfo
import com.sayut61.hockey.domain.entities.TeamPlayersInfo
import com.sayut61.hockey.domain.usecases.TeamUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class TeamPlayersDetailViewModel @Inject constructor(
    val teamUseCases: TeamUseCases
): ViewModel() {
    private val _playersLiveData = MutableLiveData<List<TeamPlayersInfo>>()
    val playersLiveData: MutableLiveData<List<TeamPlayersInfo>> = _playersLiveData

    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    fun refreshPlayersFragment(teamId: Int){
        viewModelScope.launch {
            try {
                _playersLiveData.value = teamUseCases.getPlayersInfo(teamId)
            }catch (ex: Exception){
                _errorLiveData.value = ex
            }
        }
    }
}

