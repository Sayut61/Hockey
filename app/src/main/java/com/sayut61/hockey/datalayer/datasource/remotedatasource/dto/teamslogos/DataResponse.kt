package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teamslogos

data class DataResponse(
    val fullName: String,
    val teams: List<TeamsLogosResponse>
    )