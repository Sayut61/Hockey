package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams

data class TeamsResponse (
    val teams: List<TeamInfoFromApi>
)
data class TeamInfoFromApi(
    val id: Int,
    val name: String,
    val teamName: String,
    val officialSiteUrl: String
)