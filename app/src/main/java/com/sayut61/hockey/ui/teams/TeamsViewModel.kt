package com.sayut61.hockey.ui.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.TeamGeneralInfo
import com.sayut61.hockey.domain.usecases.TeamUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
open class  TeamsViewModel @Inject constructor(
private val teamUseCases: TeamUseCases
) : ViewModel() {
    private val _teamInfoLiveData = MutableLiveData<List<TeamGeneralInfo>>()
    val teamGeneralInfoInfoLiveData: LiveData<List<TeamGeneralInfo>> = _teamInfoLiveData

    private val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> = _errorLiveData

    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarrLiveData: LiveData<Boolean> = _progressBarLiveData

    fun refreshTeamsFragment(){
        viewModelScope.launch{
            _progressBarLiveData.value = true
            try {
                _teamInfoLiveData.value = teamUseCases.getTeamsInfo()
            }catch (ex: Exception){
                _errorLiveData.value = ex
            }
            _progressBarLiveData.value = false
        }
    }
}