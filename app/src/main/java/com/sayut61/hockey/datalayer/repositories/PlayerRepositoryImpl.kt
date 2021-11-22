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
    override suspend fun getPlayersFromDB(): List<PlayerStatisticsInfo> {
        val playersFromDB = playersInfoDao.getPlayers()
        return playersFromDB.map{favoritePlayer->
            getPlayerStatById(favoritePlayer.playerId)
        }
    }
    private suspend fun getPlayerStatById(id: Int): PlayerStatisticsInfo {
        val playerStatFromApi = remoteDataSource.getPlayerStatistics(id)
        val statistics = playerStatFromApi.stats[0].splits[0].stat
        val playerFullInfo = remoteDataSource.getPlayerFullInfo(id)
        val photo = remoteDataSource.getPlayersPhoto().
        find { (playerFullInfo.fullName == it.draftKingsName) || (playerFullInfo.fullName == it.fanDuelName) || (playerFullInfo.fullName == it.yahooName) }
        return PlayerStatisticsInfo(
            id = id,
            name = playerFullInfo.fullName,
            photo = photo?.photoUrl,
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


    override suspend fun addToFavoritePlayer(playerGeneralInfo: PlayerGeneralInfo) {
        playersInfoDao.insert(FavoritePlayer(playerGeneralInfo.playerId)) }

    override suspend fun removeFromFavoritePlayer(playerGeneralInfo: PlayerGeneralInfo) {
        playersInfoDao.delete(FavoritePlayer(playerGeneralInfo.playerId)) }
}