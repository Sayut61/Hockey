package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players

import com.google.gson.annotations.SerializedName

class PlayerInfoFromSecondApi(
    @SerializedName("FanDuelName")
    val fanDuelName: String,
    @SerializedName("DraftKingsName")
    val draftKingsName: String,
    @SerializedName("YahooName")
    val yahooName: String,
    @SerializedName("PhotoUrl")
    val photoUrl: String?
)