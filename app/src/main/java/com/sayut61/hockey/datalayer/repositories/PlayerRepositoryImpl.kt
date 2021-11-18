package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.loacaldatasource.PlayersInfoDao
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoritePlayer
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.domain.PlayerRepository
import com.sayut61.hockey.domain.entities.PlayerFullInfo
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.domain.entities.PlayerStatisticsInfo
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val playersInfoDao: PlayersInfoDao
): PlayerRepository {
    override suspend fun getPlayersFromApi(): List<PlayerGeneralInfo> {
        val teams = remoteDataSource.getTeamsSecondApi()
        return remoteDataSource.getListPlayers().map{playerFromApi->
            val teamInfo = teams.find { it.shortName == playerFromApi.teamShortName }
            val isInDb = playersInfoDao.getPlayers().find{it.playerId == playerFromApi.playerId}!= null
            PlayerGeneralInfo(
                teamId = playerFromApi.teamId,
                teamFullName = playerFromApi.teamFullName,
                teamShortName = playerFromApi.teamShortName,
                jerseyNumber = playerFromApi.jerseyNumber,
                playerId = playerFromApi.playerId,
                fullName = playerFromApi.fullName,
                linkOnPlayerDetailInfo = playerFromApi.linkOnPlayerDetailInfo,
                logo = teamInfo?.wikipediaLogoUrl,
                isInFavorite = isInDb
            )
        }
    }
    override suspend fun getPlayersFromDB(): List<PlayerGeneralInfo> {
        val filteredListPlayers =remoteDataSource.getListPlayers().filter { playersFromApi->
            playersInfoDao.getPlayers().find { it.playerId == playersFromApi.playerId }!=null }
        return filteredListPlayers.map {playerGeneralInfoFromApi->
            val logo = remoteDataSource.getTeamsSecondApi().find{it.shortName == playerGeneralInfoFromApi.teamShortName }
            PlayerGeneralInfo(
                teamId = playerGeneralInfoFromApi.teamId,
                teamFullName = playerGeneralInfoFromApi.teamFullName,
                teamShortName = playerGeneralInfoFromApi.teamShortName,
                jerseyNumber = playerGeneralInfoFromApi.jerseyNumber,
                playerId = playerGeneralInfoFromApi.playerId,
                fullName = playerGeneralInfoFromApi.fullName,
                linkOnPlayerDetailInfo = playerGeneralInfoFromApi.linkOnPlayerDetailInfo,
                logo = logo!!.wikipediaLogoUrl,
                true
            )
        }
    }
    override suspend fun getPlayerFullInfo(playerId: Int): PlayerFullInfo {
        val playerInfo = remoteDataSource.getPlayerFullInfo(playerId)
        val logo = remoteDataSource.getTeamsSecondApi().find {team-> playerInfo.teamFullName.endsWith(team.shortName) }
        val photo = remoteDataSource.getPlayersPhoto().find { (playerInfo.fullName == it.draftKingsName)||(playerInfo.fullName == it.fanDuelName)||(playerInfo.fullName == it.yahooName) }
        return PlayerFullInfo(
            playerId = playerInfo.playerId,
            fullName = playerInfo.fullName,
            playerLink = playerInfo.playerLink,
            playerNumber = playerInfo.playerNumber,
            birthCity = playerInfo.birthCity,
            birthDate = playerInfo.birthDate,
            currentAge = playerInfo.currentAge,
            nationality = playerInfo.nationality,
            shootsCatches = playerInfo.shootsCatches,
            teamId = playerInfo.teamId,
            teamFullName = playerInfo.teamFullName,
            wing = playerInfo.wing,
            position = playerInfo.position,
            teamLogo = logo?.wikipediaLogoUrl,
            playerPhoto = photo?.photoUrl
        )
    }
    override suspend fun getPlayerStatistics(playerId: Int): List<PlayerStatisticsInfo> {
        val playerStatisticsInfo = remoteDataSource.getPlayerStatistics(playerId).stats.flatMap { stat->
            stat.splits.map { statistics ->
                PlayerStatisticsInfo(
                    timeOnIce = statistics.timeOnIce,
                    assists = statistics.assists,
                    goals = statistics.goals,
                    shots = statistics.shots,
                    games = statistics.games,
                    hits = statistics.hits,
                    powerPlayGoals = statistics.powerPlayGoals,
                    powerPlayPoints = statistics.powerPlayPoints,
                    powerPlayTimeOnIce = statistics.powerPlayTimeOnIce,
                    blocked = statistics.blocked,
                    plusMinus = statistics.plusMinus,
                    points = statistics.points,
                    timeOnIcePerGame = statistics.timeOnIcePerGame
                )
            }
        }
        return playerStatisticsInfo
    }

    override suspend fun addToFavoritePlayer(playerGeneralInfo: PlayerGeneralInfo) {
        playersInfoDao.insert(FavoritePlayer(playerGeneralInfo.playerId)) }

    override suspend fun removeFromFavoritePlayer(playerGeneralInfo: PlayerGeneralInfo) {
        playersInfoDao.delete(FavoritePlayer(playerGeneralInfo.playerId)) }
}