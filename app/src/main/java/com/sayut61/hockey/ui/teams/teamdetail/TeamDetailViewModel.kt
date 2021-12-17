package com.sayut61.hockey.ui.teams.teamdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.TeamFullInfo
import com.sayut61.hockey.domain.usecases.TeamsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    private val teamsUseCases: TeamsUseCases
): ViewModel() {
    private val _teamDetailLiveData = MutableLiveData<TeamFullInfo>()
    val teamDetailLiveData: MutableLiveData<TeamFullInfo> = _teamDetailLiveData

    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData

    fun refreshTeamDetailViewModel(teamId: Int){
        viewModelScope.launch {
            _progressBarLiveData.value = true
            try{
                _teamDetailLiveData.value = teamsUseCases.getTeamFullInfo(teamId)
            }catch (ex: Exception){
                _errorLiveData.value = ex
            }
            _progressBarLiveData.value = false
        }
    }
}