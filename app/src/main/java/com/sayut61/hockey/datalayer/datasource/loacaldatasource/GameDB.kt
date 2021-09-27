package com.sayut61.hockey.datalayer.datasource.loacaldatasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoriteGame

@Database(entities = [FavoriteGame::class], version = 1)
abstract class GameDB: RoomDatabase(){
    abstract fun gamesInfoDao(): GamesInfoDao
}