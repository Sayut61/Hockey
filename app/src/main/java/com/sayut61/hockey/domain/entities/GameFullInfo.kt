package com.sayut61.hockey.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameFullInfo(
    val generalInfo: GameGeneralInfo,
    val copyright: String
): Parcelable