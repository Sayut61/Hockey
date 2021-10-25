package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players

import com.google.gson.annotations.SerializedName


data class PlayerGeneralInfoFromApi(
    val teamId: Int,
    val teamFullName: String,
    val teamShortName: String,
    val jerseyNumber: Int,
    val playerId: Int,
    val fullName: String,
    val linkOnPlayerDetailInfo: String
)

fun playersGenInfoToAllPlayersGeneralInfo(playersGeneralInfo: PlayersGeneralInfo): List<PlayerGeneralInfoFromApi> {
/*    val result = mutableListOf<PlayerGeneralInfoFromApi>()
    for(team in playersGeneralInfo.teams)
        for(player in team.roster.roster){
            val player =  PlayerGeneralInfoFromApi(
                teamId = team.teamId,
                teamFullName = team.teamNameFull,
                teamShortName = team.teamNameShort,
                jerseyNumber = player.jerseyNumber,
                playerId = player.person.playerId,
                fullName = player.person.fullName,
                linkOnPlayerDetailInfo = player.person.linkOnPlayerDetailInfo
            )
            result.add(player)
        }*/

    return playersGeneralInfo.teams.flatMap { team ->
        team.roster.roster.map{player->
            PlayerGeneralInfoFromApi(
                teamId = team.teamId,
                teamFullName = team.teamNameFull,
                teamShortName = team.teamNameShort,
                jerseyNumber = player.jerseyNumber,
                playerId = player.person.playerId,
                fullName = player.person.fullName,
                linkOnPlayerDetailInfo = player.person.linkOnPlayerDetailInfo
            )
        }
    }
}

data class PlayersGeneralInfo(
    val teams: List<TeamFromApi>
)

data class TeamFromApi(
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