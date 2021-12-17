package com.sayut61.hockey.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameGeneralInfo(
    val gameDate: String,
    val linkOnDetailInfoByGame: String,
    val awayTeamNameFull: String,
    val awayTeamId: Int,
    val homeTeamNameFull: String,
    val homeTeamId: Int,
    val gameId: Int,
    val homeTeamLogo: String?,
    val awayTeamLogo: String?,
    val isInFavoriteGame: Boolean
) : Parcelable