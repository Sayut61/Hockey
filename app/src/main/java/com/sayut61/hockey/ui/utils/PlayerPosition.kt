package com.sayut61.hockey.ui.utils

fun changePositionName(position: String): String{
    return when(position){
        "Defenseman" -> "Защитник"
        "Forward" -> "Нападающий"
        "Goalie" -> "Вратарь"
        else -> ""
    }
}