package com.sayut61.hockey.datalayer.repositories

import org.junit.Assert.*

import org.junit.Test

class UtilsKtTest {

    @Test
    fun getStatusByNumberBefore() {
        val num = 1
        val result = getStatusByNumber(num)
        assertEquals("не начался", result)
    }

    @Test
    fun getStatusByNumberDuring() {
        val num = 2..6
        var result = ""
        for (i in num) {
            result = getStatusByNumber(i)
        }
        assertEquals("идет", result)
    }
    @Test
    fun getStatusByNumberAfter() {
        val num = 7
        val result = getStatusByNumber(num)
        assertEquals("окончен", result)
    }
}