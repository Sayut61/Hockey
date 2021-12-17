package com.sayut61.hockey.ui.teams.teamdetail.tabfragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.TeamPlayersInfo
import com.sayut61.hockey.domain.usecases.TeamsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamPlayersDetailViewModel @Inject constructor(
    val teamsUseCases: TeamsUseCases
) : ViewModel() {
    private val _playersLiveData = MutableLiveData<List<TeamPlayersInfo>>()
    val playersLiveData: MutableLiveData<List<TeamPlayersInfo>> = _playersLiveData

    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    fun refreshPlayersFragment(teamId: Int) {
        viewModelScope.launch {
            try {
                _playersLiveData.value = teamsUseCases.getPlayersInfo(teamId)
            } catch (ex: Exception) {
                _errorLiveData.value = ex
            }
        }
    }
}

