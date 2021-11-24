package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.domain.MapRepository
import com.sayut61.hockey.domain.entities.Stadium
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): MapRepository {
    override suspend fun getStadiumsInfo(): List<Stadium> {
        val listStadiumInfo = remoteDataSource.getStadiumInfo()
        return listStadiumInfo.map { stadiumInfo ->
            val teamName = remoteDataSource.getTeamsFirstApi().find { it.venue.nameStadium.contains(stadiumInfo.stadiumName, true) }
                Stadium(
                    nameStadium = stadiumInfo.stadiumName,
                    geoLat = stadiumInfo.GeoLat,
                    geoLong = stadiumInfo.GeoLong,
                    StadiumID = stadiumInfo.StadiumID,
                    fullTeamName = teamName?.fullTeamName
                )
        }
    }
}