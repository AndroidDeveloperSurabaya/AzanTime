package com.example.azantime.shared.api

import com.example.azantime.shared.entity.AzanEntity


interface AzanAPI {

    suspend fun getPrayerTimes(timestamp: Long, latitude: Double, longitude: Double): AzanEntity

}