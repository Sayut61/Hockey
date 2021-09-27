package com.sayut61.hockey.datalayer.datasource.loacaldatasource

import androidx.room.*
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoriteGame
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoriteTeam
@Dao
interface GamesInfoDao {
    @Query("SELECT * FROM favoriteGame")
    suspend fun getAllInfo(): List<FavoriteGame>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(id: FavoriteGame)

    @Delete
    suspend fun delete(id: FavoriteGame)
}