package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.MapRepository
import com.sayut61.hockey.domain.entities.Stadium
import com.sayut61.hockey.domain.flow.LoadingResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MapUseCases @Inject constructor(
    private val mapRepository: MapRepository
) {
    fun getStadiumInfo(): Flow<LoadingResult<List<Stadium>>>{
        return mapRepository.getStadiumsInfo()
    }
}