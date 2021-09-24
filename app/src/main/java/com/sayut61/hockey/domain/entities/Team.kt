package com.sayut61.hockey.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
    val id: Int,
    val name: String,
    val firstYearOfPlay: String,
    val teamName: String,
    val officialSiteUrl: String,
    val urlLogoTeam: String?,
    val isInFavorite: Boolean
) : Parcelable
