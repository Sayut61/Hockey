package com.sayut61.hockey.datalayer.repositories

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class MapRepositoryImplTest {
    lateinit var mapRepositoryImpl: MapRepositoryImpl
    @Before
    fun setUp() {
        mapRepositoryImpl = MapRepositoryImpl(FakeDataSource())
    }

    @Test
    fun getStadiumsInfo() {

        mapRepositoryImpl.getStadiumsInfo()
        mapRepositoryImpl.counter = 10
    }


    @Test
    fun getStadiumsInfo() {
        mapRepositoryImpl.getStadiumsInfo()
    }
    @Test
    fun getStadiumsInfo() {
        mapRepositoryImpl.getStadiumsInfo()
    }
}

class FakeDataSource