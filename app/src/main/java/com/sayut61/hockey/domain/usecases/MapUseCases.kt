package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.MapRepository
import com.sayut61.hockey.domain.entities.Stadium
import javax.inject.Inject

class MapUseCases @Inject constructor(
    private val mapRepository: MapRepository
) {
    suspend fun getStadiumInfo(): List<Stadium>{
        return mapRepository.getStadiumsInfo()
    }
}