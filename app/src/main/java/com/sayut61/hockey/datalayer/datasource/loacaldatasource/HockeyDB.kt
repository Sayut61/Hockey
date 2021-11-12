package com.sayut61.hockey.datalayer.datasource.loacaldatasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoriteGame
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoritePlayer

@Database(entities = [FavoriteGame::class], version = 1)
abstract class HockeyGameDB: RoomDatabase() {
    abstract fun gamesInfoDao(): GamesInfoDao
}
@Database(entities = [FavoritePlayer::class], version = 1)
abstract class HockeyPlayerDB: RoomDatabase() {
    abstract fun playersInfoDao(): PlayersInfoDao
}
