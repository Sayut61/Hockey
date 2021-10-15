package com.sayut61.hockey.ui.calendar.calendardetail.adapters_and_recyclerFragment

import androidx.lifecycle.ViewModel
import com.sayut61.hockey.domain.usecases.GamesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeTeamRecyclerViewModel @Inject constructor(
    val gamesUseCases: GamesUseCases
): ViewModel() {
}