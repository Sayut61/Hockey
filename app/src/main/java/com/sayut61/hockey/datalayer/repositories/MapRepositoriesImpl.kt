package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.domain.MapRepositories
import com.sayut61.hockey.domain.entities.Stadium
import javax.inject.Inject

class MapRepositoriesImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): MapRepositories {
    override suspend fun getStadiumsInfo(): List<Stadium> {
        val listStadiumInfo = remoteDataSource.getStadiumInfo()
        return listStadiumInfo.map { stadiumInfo ->
                Stadium(
                    nameStadium = stadiumInfo.stadiumName,
                    geoLat = stadiumInfo.GeoLat,
                    geoLong = stadiumInfo.GeoLong,
                    StadiumID = stadiumInfo.StadiumID
                )
        }
    }
}