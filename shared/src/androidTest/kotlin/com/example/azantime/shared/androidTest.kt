package com.example.azantime.shared

import kotlinx.datetime.Clock
import org.junit.Assert.assertTrue
import org.junit.Test

class AndroidGreetingTest {

    @Test
    fun testExample() {
        val currentMoment = Clock.System.now()
        println(currentMoment)
        println(currentMoment.toEpochMilliseconds())
//        assertTrue("Check Android is mentioned", Greeting().greeting().contains("Android"))
    }

}