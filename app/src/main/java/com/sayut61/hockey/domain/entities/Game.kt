package com.sayut61.hockey.domain.entities

import android.os.Parcelable
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar.LinkByGame
import dagger.multibindings.IntoMap
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
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
): Parcelable