package com.sayut61.hockey.ui.teams.teamdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.TeamFullInfo
import com.sayut61.hockey.domain.usecases.TeamUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class TeamStatisticDetailViewModel @Inject constructor(
   private val teamUseCases: TeamUseCases
): ViewModel() {
    private val _teamStatisticLiveData = MutableLiveData<TeamFullInfo>()
    val teamStatisticLiveData: MutableLiveData<TeamFullInfo> = _teamStatisticLiveData

    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData

    fun refreshStatFragment(id: Int){
        viewModelScope.launch {
            _progressBarLiveData.value = true
            try {
                _teamStatisticLiveData.value = teamUseCases.getTeamFullInfo(id)
            }catch (ex: Exception) {
                _errorLiveData.value = ex
            }
            _progressBarLiveData.value = false
        }
    }
}