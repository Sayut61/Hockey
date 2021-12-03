package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.loacaldatasource.PlayersInfoDao
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoritePlayer
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.PlayerInfoFromSecondApi
import com.sayut61.hockey.domain.PlayerRepository
import com.sayut61.hockey.domain.entities.PlayerFullInfo
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.domain.entities.PlayerStatisticsInfo
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val playersInfoDao: PlayersInfoDao
) : PlayerRepository {
    override suspend fun getPlayersFromApi(): List<PlayerGeneralInfo> {
        val teams = remoteDataSource.getTeamsSecondApi()
        return remoteDataSource.getListPlayers().map { playerFromApi ->
            val teamInfo = teams.find { it.shortName == playerFromApi.teamShortName }
            val isInDb =
                playersInfoDao.getPlayers().find { it.playerId == playerFromApi.playerId } != null
            PlayerGeneralInfo(
                teamId = playerFromApi.teamId,
                teamFullName = playerFromApi.teamFullName,
                teamShortName = playerFromApi.teamShortName,
                jerseyNumber = playerFromApi.jerseyNumber,
                playerId = playerFromApi.playerId,
                fullName = playerFromApi.fullName,
                linkOnPlayerDetailInfo = playerFromApi.linkOnPlayerDetailInfo,
                logo = teamInfo?.wikipediaLogoUrl,
                isInFavorite = isInDb,
            )
        }
    }
    override suspend fun getPlayersFromDB(): List<PlayerStatisticsInfo> {
        val playersFromDB = playersInfoDao.getPlayers()
        val photos = remoteDataSource.getPlayersPhotos()
        return playersFromDB.map { favoritePlayer ->
            getPlayerStatById(favoritePlayer, photos)
        }
    }
    override suspend fun getPlayerStat(playerFullInfo: PlayerFullInfo): PlayerStatisticsInfo {
        val photos = remoteDataSource.getPlayersPhotos()
        return getPlayerStatById(FavoritePlayer(playerFullInfo.playerId, playerFullInfo.fullName),photos)
    }
    private suspend fun getPlayerStatById(favoritePlayer: FavoritePlayer, photos: List<PlayerInfoFromSecondApi>): PlayerStatisticsInfo {
        val photo = photos
            .find { (favoritePlayer.fullName == it.draftKingsName) || (favoritePlayer.fullName == it.fanDuelName) || (favoritePlayer.fullName == it.yahooName) }
        val playerStatFromApi = remoteDataSource.getPlayerStatistics(favoritePlayer.playerId)
        return if (playerStatFromApi.stats.isEmpty() || playerStatFromApi.stats[0].splits.isEmpty())
            PlayerStatisticsInfo(
                id = favoritePlayer.playerId,
                name = favoritePlayer.fullName,
                photo = photo?.photoUrl
            )
        else {
            val statistics = playerStatFromApi.stats[0].splits[0].stat
            PlayerStatisticsInfo(
                id = favoritePlayer.playerId,
                name = favoritePlayer.fullName,
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
    }
    override suspend fun getPlayerFullInfo(playerId: Int): PlayerFullInfo {
        val playerInfo = remoteDataSource.getPlayerFullInfo(playerId)
        val logo = remoteDataSource.getTeamsSecondApi()
            .find { team -> playerInfo.teamFullName.endsWith(team.shortName) }
        val photo = remoteDataSource.getPlayersPhotos()
            .find { (playerInfo.fullName == it.draftKingsName) || (playerInfo.fullName == it.fanDuelName) || (playerInfo.fullName == it.yahooName) }
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
        playersInfoDao.insert(FavoritePlayer(playerGeneralInfo.playerId, playerGeneralInfo.fullName))
    }
    override suspend fun removeFromFavoritePlayer(playerId: Int) {
        playersInfoDao.delete(FavoritePlayer(playerId,""))
    }
}