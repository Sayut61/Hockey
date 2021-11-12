package com.sayut61.hockey.datalayer.datasource.loacaldatasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoriteGame
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoritePlayer

@Database(entities = [FavoriteGame::class, FavoritePlayer::class], version = 1)
abstract class HockeyDB: RoomDatabase() {
    abstract fun gamesInfoDao(): GamesInfoDao
    abstract fun playersInfoDao(): PlayersInfoDao
}
