package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players

import com.google.gson.annotations.SerializedName

data class AllPlayersGeneralInfo(
    val teamId: Int,
    val teamFullName: String,
    val teamShortName: String,
    val jerseyNumber: List<Int>,
    val playerId: List<Int>,
    val fullName: List<String>,
    val linkOnPlayerDetailInfo: List<String>
    )

fun playersGenInfoToAllPlayersGeneralInfo(playersGeneralInfo: PlayersGeneralInfo): List<AllPlayersGeneralInfo>{
    return playersGeneralInfo.teams.map{player->
        AllPlayersGeneralInfo(
            teamId = player.teamId,
            teamFullName = player.teamNameFull,
            teamShortName = player.teamNameShort,
            jerseyNumber = player.roster.roster.map { it.jerseyNumber},
            playerId = player.roster.roster.map{it.person.playerId},
            fullName = player.roster.roster.map{it.person.fullName},
            linkOnPlayerDetailInfo = player.roster.roster.map{it.person.linkOnPlayerDetailInfo}
        )
    }
}

data class PlayersGeneralInfo (
    val teams: List<Teams>
        )
data class Teams(
    @SerializedName("id")
    val teamId: Int,
    @SerializedName("name")
    val teamNameFull: String,
    @SerializedName("teamName")
    val teamNameShort: String,
    val roster: Roster
)
data class Roster(
   val roster: List<PlayersByTeam>
)
data class PlayersByTeam(
    val jerseyNumber: Int,
    val person: PersonInfo
)
data class PersonInfo(
    @SerializedName("id")
    val playerId: Int,
    val fullName: String,
    @SerializedName("link")
    val linkOnPlayerDetailInfo: String
)