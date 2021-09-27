package com.sayut61.hockey.domain.entities

import android.os.Parcelable
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar.LinkByGame
import dagger.multibindings.IntoMap
import kotlinx.parcelize.Parcelize

@Parcelize
class Calendar(
    val gameDate: String,
    val linkOnDetailInfoByGame: String,
    val awayTeamName: String,
    val awayTeamId: Int,
    val homeTeamName: String,
    val homeTeamId: Int,
    val gameId: Int,
    val inInFavorite: Boolean
): Parcelable