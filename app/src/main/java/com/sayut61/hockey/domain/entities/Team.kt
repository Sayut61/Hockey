package com.sayut61.hockey.domain.entities

import android.os.Parcelable
import com.google.android.gms.common.internal.StringResourceValueReader
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
    val id: Int,
    val fullTeamName: String,
    val shortTeamName: String,
    val cityName: String?,
    val officialSiteUrl: String,
    val urlLogoTeam: String?,
    val isInFavoriteTeam: Boolean,
    val StadiumID: Int
) : Parcelable
