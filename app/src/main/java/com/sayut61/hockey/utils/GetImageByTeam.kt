package com.sayut61.hockey.utils

import com.sayut61.hockey.R
import com.sayut61.hockey.domain.entities.Team

fun getImageByTeam(teamId: Int): Int{
    return when(teamId){
        1-> R.drawable.arizona_coyotes
        2-> R.drawable.avalanche
        else -> R.drawable.boston
    }
}