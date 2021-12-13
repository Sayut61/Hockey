package com.sayut61.hockey.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayut61.hockey.domain.entities.Stadium
import com.sayut61.hockey.domain.flow.LoadingResult
import com.sayut61.hockey.domain.usecases.MapUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val mapUseCases: MapUseCases
) : ViewModel() {

    private val _mapLiveData = MutableLiveData<List<Stadium>>()
    val mapLiveData: LiveData<List<Stadium>> = _mapLiveData
    private val _exceptionLiveData = MutableLiveData<Exception>()
    val exceptionLiveData: LiveData<Exception> = _exceptionLiveData
    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    fun refreshListStadium() {
        viewModelScope.launch {
            mapUseCases.getStadiumInfo().collect {
                when (it) {
                    is LoadingResult.SuccessResult -> _mapLiveData.value = it.data!!
                    is LoadingResult.ErrorResult -> _exceptionLiveData.value = it.error
                    is LoadingResult.Loading -> _isLoadingLiveData.value = it.isLoading
                }
            }
        }
    }
}