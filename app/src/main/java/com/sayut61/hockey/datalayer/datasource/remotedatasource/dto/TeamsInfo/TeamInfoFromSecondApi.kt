package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.TeamsInfo

import com.google.gson.annotations.SerializedName

data class TeamInfoFromSecondApi(
    @SerializedName("Name")
    val shortName: String,
    @SerializedName("City")
    val cityName: String,
    @SerializedName("WikipediaLogoUrl")
    val wikipediaLogoUrl: String,
    @SerializedName("StadiumID")
    val stadiumID: Int
)