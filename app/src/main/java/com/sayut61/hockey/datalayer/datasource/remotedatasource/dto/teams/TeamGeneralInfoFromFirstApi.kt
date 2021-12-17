package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams

import com.google.gson.annotations.SerializedName

data class TeamsFromFirstApi(
    val teams: List<TeamInfoFromFirstApi>
)

data class TeamInfoFromFirstApi(
    val id: Int,
    @SerializedName("name")
    val fullTeamName: String,
    @SerializedName("teamName")
    val shortTeamName: String,
    val officialSiteUrl: String,
    val venue: Venue
)

data class Venue(
    @SerializedName("name")
    val nameStadium: String
)