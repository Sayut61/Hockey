package com.sayut61.hockey.datalayer.datasource.loacaldatasource

import androidx.room.*
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoriteTeam
@Dao
interface TeamsInfoDao {
    @Query("SELECT * FROM favoriteTeam")
    suspend fun getAllInfo(): List<FavoriteTeam>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(id: FavoriteTeam)

    @Delete
    suspend fun delete(id: FavoriteTeam)
}