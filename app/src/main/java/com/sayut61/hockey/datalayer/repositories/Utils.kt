package com.sayut61.hockey.datalayer.repositories

fun getStatusByNumber(statusNumber: Int): String{
    return when(statusNumber){
        1->"не начался"
        in 2..6->"идет"
        7-> "окончен"
        else -> "статус не известен"
    }
}