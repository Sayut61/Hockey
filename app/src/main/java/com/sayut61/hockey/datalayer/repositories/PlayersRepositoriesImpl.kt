package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.loacaldatasource.PlayersInfoDao
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoritePlayer
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.domain.PlayersRepositories
import com.sayut61.hockey.domain.entities.Players
import javax.inject.Inject

class PlayersRepositoriesImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val playersInfoDao: PlayersInfoDao
): PlayersRepositories {
    override suspend fun getPlayersFromApi(): List<Players> {
        return remoteDataSource.getListPlayers().map{
            Players(
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
    override suspend fun getPlayersFromDB(): List<Players> {
        val filteredListPlayers =remoteDataSource.getListPlayers().filter { playersFromApi->
            playersInfoDao.getPlayers().find { it.id == playersFromApi.teamId }!=null
        }
        return filteredListPlayers.map {
            Players(
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
    override suspend fun addToFavoritePlayer(id: Players) {
        playersInfoDao.insert(FavoritePlayer(id.teamId))
    }
    override suspend fun removeFromFavoritePlayer(id: Players) {
        playersInfoDao.delete(FavoritePlayer(id.teamId))
    }
}