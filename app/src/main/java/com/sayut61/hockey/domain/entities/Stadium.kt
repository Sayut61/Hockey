package com.sayut61.hockey.domain.entities

data class Stadium(
    val nameStadium: String,
    val geoLat: Double,
    val geoLong: Double,
    val StadiumID: Int,
    val fullTeamName: String?
)