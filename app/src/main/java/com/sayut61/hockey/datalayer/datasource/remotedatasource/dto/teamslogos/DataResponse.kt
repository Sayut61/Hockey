package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teamslogos

data class DataResponse(
    val data: List<Franchise>
    )

data class Franchise (
    val teams: List<TeamLogos>
)

data class TeamLogos (
    val logos: List<LogoFromApi>
        )

data class LogoFromApi(
    val teamId: Int,
    val url: String,
    val endSeason: Int
)