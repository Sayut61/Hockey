package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players

import com.google.gson.annotations.SerializedName
import java.lang.Exception

data class PlayerFullInfoFromApi(
    val playerId: Int,
    val fullName: String,
    val playerLink: String,
    val playerNumber: String,
    val birthDate: String,
    val currentAge: Int,
    val birthCity: String,
    val nationality: String,
    val shootsCatches: String,
    val teamId: Int,
    val teamFullName: String,
    val wing: String,
    val position: String
)

fun playerInfoToPlayerFullInfo(playerFull: PlayerFullInfoResponse): PlayerFullInfoFromApi {
    val playerInfo = playerFull.people.map {
        PlayerFullInfoFromApi(
            playerId = it.playerId,
            fullName = it.fullName,
            playerLink = it.playerLink,
            playerNumber = it.playerNumber,
            birthDate = it.birthDate,
            currentAge = it.currentAge,
            birthCity = it.birthCity,
            nationality = it.nationality,
            shootsCatches = it.shootsCatches,
            teamId = it.currentTeam.teamId,
            teamFullName = it.currentTeam.teamFullName,
            wing = it.primaryPosition.wing,
            position = it.primaryPosition.position
        )
    }
    return if (playerInfo.isNotEmpty()) {
        playerInfo[0]
    } else {
        throw Exception("error get player info")
    }
}

data class PlayerFullInfoResponse(
    val people: List<PeopleInfo>
)

data class PeopleInfo(
    @SerializedName("id")
    val playerId: Int,
    val fullName: String,
    @SerializedName("link")
    val playerLink: String,
    @SerializedName("primaryNumber")
    val playerNumber: String,
    val birthDate: String,
    val currentAge: Int,
    val birthCity: String,
    val nationality: String,
    val shootsCatches: String,
    val currentTeam: TeamInfo,
    val primaryPosition: PlayerPosition
)

data class TeamInfo(
    @SerializedName("id")
    val teamId: Int,
    @SerializedName("name")
    val teamFullName: String
)

data class PlayerPosition(
    @SerializedName("name")
    val wing: String,
    @SerializedName("type")
    val position: String
)