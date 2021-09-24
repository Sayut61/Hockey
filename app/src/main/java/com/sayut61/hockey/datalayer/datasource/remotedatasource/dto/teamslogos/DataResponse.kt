package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teamslogos

import com.google.gson.annotations.SerializedName

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
    @SerializedName("url")
    val urlLogoTeam: String,
    val endSeason: Int
)