package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.stadium

import com.google.gson.annotations.SerializedName

data class Stadium(
    @SerializedName("Name")
    val stadiumName: String,
    val GeoLat: Double,
    val GeoLong: Double,
    val StadiumID: Int
)