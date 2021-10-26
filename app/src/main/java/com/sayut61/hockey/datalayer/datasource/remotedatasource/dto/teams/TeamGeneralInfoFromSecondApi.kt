package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams

import com.google.gson.annotations.SerializedName

data class TeamGeneralInfoFromSecondApi(
    @SerializedName("Name")
    val shortName: String,
    @SerializedName("City")
    val cityName: String,
    @SerializedName("WikipediaLogoUrl")
    val wikipediaLogoUrl: String,
    @SerializedName("StadiumID")
    val stadiumID: Int
)