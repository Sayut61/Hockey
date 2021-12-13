package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.Stadium
import com.sayut61.hockey.domain.flow.LoadingResult
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    fun getStadiumsInfo(): Flow<LoadingResult<List<Stadium>>>
}