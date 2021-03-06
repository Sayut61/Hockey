package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams

import com.google.gson.annotations.SerializedName


data class TeamPlayersFromApi(
    val jerseyNumber: Int,
    val playerId: Int,
    val fullName: String,
    val linkOnFullInfoByPlayer: String,
    val type: String
)

fun teamPlayersInfoFromApiToTeamPlayers(playersFromFirstApiResponse: TeamPlayersFromFirstApiResponse): List<TeamPlayersFromApi> {
    return playersFromFirstApiResponse.roster.map {
        TeamPlayersFromApi(
            jerseyNumber = it.jerseyNumber,
            playerId = it.person.playerId,
            fullName = it.person.fullName,
            linkOnFullInfoByPlayer = it.person.linkOnFullInfoByPlayer,
            type = it.position.type
        )
    }
}

data class TeamPlayersFromFirstApiResponse(
    val roster: List<PersonInfoFromApi>
)

data class PersonInfoFromApi(
    val jerseyNumber: Int,
    val person: PersonInf,
    val position: PersonPos
)

data class PersonInf(
    @SerializedName("id")
    val playerId: Int,
    val fullName: String,
    @SerializedName("link")
    val linkOnFullInfoByPlayer: String
)

data class PersonPos(
    val type: String
)