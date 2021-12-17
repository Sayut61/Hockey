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
class TeamStatisticDetailViewModel @Inject constructor(
   private val teamsUseCases: TeamsUseCases
): ViewModel() {
    private val _teamStatisticLiveData = MutableLiveData<TeamFullInfo>()
    val teamStatisticLiveData: MutableLiveData<TeamFullInfo> = _teamStatisticLiveData

    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    fun refreshStatFragment(id: Int){
        viewModelScope.launch {
            try {
                _teamStatisticLiveData.value = teamsUseCases.getTeamFullInfo(id)
            }catch (ex: Exception) {
                _errorLiveData.value = ex
            }
        }
    }
}