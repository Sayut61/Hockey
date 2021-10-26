package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.Stadium

interface MapRepository {
    suspend fun getStadiumsInfo(): List<Stadium>
}