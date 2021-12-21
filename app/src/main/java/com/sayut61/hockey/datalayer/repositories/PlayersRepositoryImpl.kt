package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.loacaldatasource.PlayersInfoDao
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoritePlayer
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSourceImpl
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.PlayerPhoto
import com.sayut61.hockey.domain.PlayersRepository
import com.sayut61.hockey.domain.entities.PlayerFullInfo
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.domain.entities.PlayerStatisticsInfo
import com.sayut61.hockey.domain.flow.LoadingResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayersRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSourceImpl,
    private val playersInfoDao: PlayersInfoDao
) : PlayersRepository {
    var cachePlayers: List<PlayerGeneralInfo>? = null
    override fun getPlayersFromApi(): Flow<LoadingResult<List<PlayerGeneralInfo>>> = flow {
        emit(LoadingResult.Loading(true))
        try {
            cachePlayers?.let {
                emit(LoadingResult.SuccessResult(it))
                emit(LoadingResult.Loading(false))
            }
            val teams = remoteDataSource.getAllTeamsFromSecondApi()
            val result = remoteDataSource.getAllPlayers().map { playerFromApi ->
                val teamInfo = teams.find { it.shortName == playerFromApi.teamShortName }
                val isInDb = playersInfoDao.getPlayers()
                    .find { it.playerId == playerFromApi.playerId } != null
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
            var equals = true
            for (i in 0..result.lastIndex)
                if (result[i] != cachePlayers?.get(i)) {
                    equals = false
                    break
                }
            if (!equals) {
                cachePlayers = result
                emit(LoadingResult.SuccessResult(result))
            }
            emit(LoadingResult.Loading(false))
        } catch (ex: Exception) {
            emit(LoadingResult.ErrorResult(java.lang.Exception("Ошибка загрузки списка игроков")))
            emit(LoadingResult.Loading(false))
        }
    }

    var cacheFavoritePlayers: List<PlayerStatisticsInfo>? = null
    override fun getPlayersFromDB(): Flow<LoadingResult<List<PlayerStatisticsInfo>>> = flow {
        emit(LoadingResult.Loading(true))
        try {
            cacheFavoritePlayers?.let {
                emit(LoadingResult.SuccessResult(it))
                emit(LoadingResult.Loading(false))
            }
            val playersFromDB = playersInfoDao.getPlayers()
            val photos = remoteDataSource.getPlayersPhoto()
            val result = playersFromDB.map { favoritePlayer ->
                getPlayerStatById(favoritePlayer, photos)
            }
            var equals = true
            for (i in 0..result.lastIndex)
                if (result[0] != cacheFavoritePlayers?.get(i)) {
                    equals = false
                    break
                }
            if (!equals) {
                cacheFavoritePlayers = result
                emit(LoadingResult.SuccessResult(result))
            }
            emit(LoadingResult.Loading(false))
        } catch (ex: java.lang.Exception) {
            emit(LoadingResult.ErrorResult(java.lang.Exception("Ошибка загрузки избранных игроков")))
            emit(LoadingResult.Loading(false))
        }
    }

    override suspend fun getPlayerStat(playerFullInfo: PlayerFullInfo): PlayerStatisticsInfo {
        val photos = remoteDataSource.getPlayersPhoto()
        return getPlayerStatById(
            FavoritePlayer(playerFullInfo.playerId, playerFullInfo.fullName),
            photos
        )
    }

    private suspend fun getPlayerStatById(
        favoritePlayer: FavoritePlayer,
        photos: List<PlayerPhoto>
    ): PlayerStatisticsInfo {
        val photo = photos
            .find { (favoritePlayer.fullName == it.draftKingsName) || (favoritePlayer.fullName == it.fanDuelName) || (favoritePlayer.fullName == it.yahooName) }
        val playerStatFromApi = remoteDataSource.getPlayerStatistic(favoritePlayer.playerId)
        return if (playerStatFromApi.stats.isEmpty() || playerStatFromApi.stats[0].splits.isEmpty())
            PlayerStatisticsInfo(
                id = favoritePlayer.playerId,
                name = favoritePlayer.fullName,
                photo = photo?.photoUrl,
                teamLogo = getPlayerFullInfo(favoritePlayer.playerId).teamLogo
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
                timeOnIcePerGame = statistics.timeOnIcePerGame,
                teamLogo = getPlayerFullInfo(favoritePlayer.playerId).teamLogo
            )
        }
    }

    override suspend fun getPlayerFullInfo(playerId: Int): PlayerFullInfo {
        val playerInfo = remoteDataSource.getPlayerFullInfo(playerId)
        val logo = remoteDataSource.getAllTeamsFromSecondApi()
            .find { team -> playerInfo.teamFullName.endsWith(team.shortName) }
        val photo = remoteDataSource.getPlayersPhoto()
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
        playersInfoDao.insert(
            FavoritePlayer(
                playerGeneralInfo.playerId,
                playerGeneralInfo.fullName
            )
        )
    }

    override suspend fun removeFromFavoritePlayer(playerId: Int) {
        playersInfoDao.delete(FavoritePlayer(playerId, ""))
    }
}