package com.sayut61.hockey.ui.utils

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class FunToTestKtTest {

    @Before
    fun setUp() {
    }

    @Test
    fun `if firstNumber more return firstnumber`() {
        val firstNumber = 10
        val secondNumber = 20
        val result = maxNumber(firstNumber, secondNumber)
        assertEquals(20, result)
    }
    @Test
    fun `if secondNumber more return secondNumber`() {
        val firstNumber = 80
        val secondNumber = 20
        val result = maxNumber(firstNumber, secondNumber)
        assertEquals(80, result)
    }
}