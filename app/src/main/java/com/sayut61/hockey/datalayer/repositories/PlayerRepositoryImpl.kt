package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.loacaldatasource.PlayersInfoDao
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoritePlayer
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.domain.PlayerRepository
import com.sayut61.hockey.domain.entities.Player
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val playersInfoDao: PlayersInfoDao
): PlayerRepository {
    override suspend fun getPlayersFromApi(): List<Player> {
        val teams = remoteDataSource.getTeamsSecondApi()
        return remoteDataSource.getListPlayers().map{playerFromApi->
            val teamInfo = teams.find { it.shortName == playerFromApi.teamShortName }
            Player(
                teamId = playerFromApi.teamId,
                teamFullName = playerFromApi.teamFullName,
                teamShortName = playerFromApi.teamShortName,
                jerseyNumber = playerFromApi.jerseyNumber,
                playerId = playerFromApi.playerId,
                fullName = playerFromApi.fullName,
                linkOnPlayerDetailInfo = playerFromApi.linkOnPlayerDetailInfo,
                logo = teamInfo?.wikipediaLogoUrl
            )
        }
    }
    override suspend fun getPlayersFromDB(): List<Player> {
        val filteredListPlayers =remoteDataSource.getListPlayers().filter { playersFromApi->
            playersInfoDao.getPlayers().find { it.teamId == playersFromApi.teamId }!=null
        }
        return filteredListPlayers.map {playerGeneralInfoFromApi->
            val logo = remoteDataSource.getTeamsSecondApi().find{it.shortName == playerGeneralInfoFromApi.teamShortName }
            Player(
                teamId = playerGeneralInfoFromApi.teamId,
                teamFullName = playerGeneralInfoFromApi.teamFullName,
                teamShortName = playerGeneralInfoFromApi.teamShortName,
                jerseyNumber = playerGeneralInfoFromApi.jerseyNumber,
                playerId = playerGeneralInfoFromApi.playerId,
                fullName = playerGeneralInfoFromApi.fullName,
                linkOnPlayerDetailInfo = playerGeneralInfoFromApi.linkOnPlayerDetailInfo,
                logo = logo!!.wikipediaLogoUrl
            )
        }
    }
    override suspend fun addToFavoritePlayer(player: Player) {
        playersInfoDao.insert(playerToFavoritePlayer(player))
    }
    override suspend fun removeFromFavoritePlayer(player: Player) {
        playersInfoDao.delete(playerToFavoritePlayer(player))
    }
    fun playerToFavoritePlayer(player: Player): FavoritePlayer{
        return FavoritePlayer(
            player.teamId,
            player.teamFullName,
            player.teamShortName,
            player.jerseyNumber,
            player.playerId,
            player.fullName,
            player.linkOnPlayerDetailInfo,
            player.logo
        )
    }
    fun favoritePlayerToPlayer(favoritePlayer: FavoritePlayer): Player{
        return Player(
            favoritePlayer.teamId,
            favoritePlayer.teamFullName,
            favoritePlayer.teamShortName,
            favoritePlayer.jerseyNumber,
            favoritePlayer.playerId,
            favoritePlayer.fullName,
            favoritePlayer.linkOnPlayerDetailInfo,
            favoritePlayer.logo
        )
    }
}