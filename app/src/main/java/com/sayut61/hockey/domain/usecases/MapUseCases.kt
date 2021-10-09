package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.MapRepositories
import com.sayut61.hockey.domain.entities.Stadium
import javax.inject.Inject

class MapUseCases @Inject constructor(
    private val mapRepositories: MapRepositories
) {
    suspend fun getStadiumInfo(): List<Stadium>{
        return mapRepositories.getStadiumsInfo()
    }
}