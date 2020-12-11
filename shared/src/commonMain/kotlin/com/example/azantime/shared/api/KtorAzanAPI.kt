package com.example.azantime.shared.api

import com.example.azantime.shared.entity.AzanEntity
import io.ktor.client.*
import io.ktor.client.request.*


class KtorAzanAPI(private val httpClient: HttpClient) : AzanAPI {

    companion object {
        private const val BASE_URL = "https://api.aladhan.com/v1"
    }

    override suspend fun getPrayerTimes(timestamp: Long, latitude: Double, longitude: Double): AzanEntity {
        val url = "$BASE_URL/timings/${timestamp}?latitude=${latitude}&longitude=${longitude}&method=5"
        return httpClient.get(url)
    }
}