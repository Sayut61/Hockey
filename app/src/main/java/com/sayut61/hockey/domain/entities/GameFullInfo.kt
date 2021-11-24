package com.sayut61.hockey.domain.entities

import android.os.Parcelable
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.games.PeriodsInfo
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.games.PrimaryPosition
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameFullInfo(
    val generalInfo: GameGeneralInfo,
    val playersAwayTeam: List<PlayerNameAndNumber>?,
    val playersHomeTeam: List<PlayerNameAndNumber>?,
    val currentPeriod: Int,
    val currentPeriodOrdinal: String,
    val currentPeriodTimeRemaining: String,
    val periods: List<PeriodsInfo>,
    val homeTeamGoalsByPeriods: List<Int>,
    val homeTeamShotsOnGoalByPeriods: List<Int>,
    val awayTeamGoalsByPeriods: List<Int>,
    val awayTeamShotsOnGoalByPeriods: List<Int>,
    val goalsAwayTeam: Int,
    val shotsAwayTeam: Int,
    val blockedAwayTeam: Int,
    val hitsAwayTeam: Int,
    val goalsHomeTeam: Int,
    val shotsHomeTeam: Int,
    val blockedHomeTeam: Int,
    val hitsHomeTeam: Int,
    val gameState: String
): Parcelable

@Parcelize
data class PlayerNameAndNumber(
    val name: String,
    val number: String,
    val id: Int,
    val primaryPosition: String,
    val typePosition: String
): Parcelable