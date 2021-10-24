package com.sayut61.hockey.datalayer.datasource.loacaldatasource

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoritePlayer

interface PlayersInfoDao {
    @Query("SELECT * FROM favoritePlayer" )
    suspend fun getPlayers(): List<FavoritePlayer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(id: FavoritePlayer)

    @Delete
    suspend fun delete(id: FavoritePlayer)
}