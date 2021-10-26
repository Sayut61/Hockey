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
        return remoteDataSource.getListPlayers().map{playerFromApi->
            Player(
                teamId = playerFromApi.teamId,
                teamFullName = playerFromApi.teamFullName,
                teamShortName = playerFromApi.teamShortName,
                jerseyNumber = playerFromApi.jerseyNumber,
                playerId = playerFromApi.playerId,
                fullName = playerFromApi.fullName,
                linkOnPlayerDetailInfo = playerFromApi.linkOnPlayerDetailInfo
            )
        }
    }
    override suspend fun getPlayersFromDB(): List<Player> {
        val filteredListPlayers =remoteDataSource.getListPlayers().filter { playersFromApi->
            playersInfoDao.getPlayers().find { it.id == playersFromApi.teamId }!=null
        }
        return filteredListPlayers.map {
            Player(
                teamId = it.teamId,
                teamFullName = it.teamFullName,
                teamShortName = it.teamShortName,
                jerseyNumber = it.jerseyNumber,
                playerId = it.playerId,
                fullName = it.fullName,
                linkOnPlayerDetailInfo = it.linkOnPlayerDetailInfo
            )
        }
    }
    override suspend fun addToFavoritePlayer(id: Player) {
        playersInfoDao.insert(FavoritePlayer(id.teamId))
    }
    override suspend fun removeFromFavoritePlayer(id: Player) {
        playersInfoDao.delete(FavoritePlayer(id.teamId))
    }
}