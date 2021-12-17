package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.domain.MapRepository
import com.sayut61.hockey.domain.entities.Stadium
import com.sayut61.hockey.domain.flow.LoadingResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : MapRepository {
    var counter = 0
    var cacheMap: List<Stadium>? = null
    override fun getStadiumsInfo(): Flow<LoadingResult<List<Stadium>>> = flow {
        emit(LoadingResult.Loading(true))
        try {
            cacheMap?.let {
                emit(LoadingResult.SuccessResult(it))
                emit(LoadingResult.Loading(false))
            }
            val listStadiumInfo = remoteDataSource.getStadiums()
            val result = listStadiumInfo.map { stadiumInfo ->
                val teamName = remoteDataSource.getAllTeamsFromFirstApi()
                    .find { it.venue.nameStadium.contains(stadiumInfo.stadiumName, true) }
                Stadium(
                    nameStadium = stadiumInfo.stadiumName,
                    geoLat = stadiumInfo.GeoLat,
                    geoLong = stadiumInfo.GeoLong,
                    StadiumID = stadiumInfo.StadiumID,
                    fullTeamName = teamName?.fullTeamName
                )
            }
            var equals = true
            for (i in 0..result.lastIndex)
                if (result[0] != cacheMap?.get(i)) {
                    equals = false
                    break
                }
            if (!equals) {
                cacheMap = result
                emit(LoadingResult.SuccessResult(result))
            }
            emit(LoadingResult.Loading(false))
        } catch (ex: Exception) {
            emit(LoadingResult.ErrorResult(Exception("Ошибка загрузки карты")))
            emit(LoadingResult.Loading(false))
        }
    }

}