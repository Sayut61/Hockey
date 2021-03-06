package com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoritePlayer(
    @PrimaryKey
    val playerId: Int,
    val fullName: String
)