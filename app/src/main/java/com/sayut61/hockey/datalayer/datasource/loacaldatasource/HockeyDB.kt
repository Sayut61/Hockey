package com.sayut61.hockey.datalayer.datasource.loacaldatasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoriteTeam

@Database(entities = [FavoriteTeam::class], version = 1)
abstract class CovidDB: RoomDatabase(){
    abstract fun teamsInfoDao(): TeamsInfoDao
}