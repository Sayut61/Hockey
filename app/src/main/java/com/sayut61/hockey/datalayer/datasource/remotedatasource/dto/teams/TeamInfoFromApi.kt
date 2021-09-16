package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams

data class TeamInfoFromApi(
    val id: Int,
    val name: String,
    val firstYearOfPlay: String,
    val teamName: String,
    val officialSiteUrl: String
)