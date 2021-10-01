package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.Stadium

interface MapRepositories {
suspend fun getStadiumInfo(): List<Stadium>
}