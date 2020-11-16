package com.example.azantime.shared


import io.ktor.client.request.*
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import io.ktor.client.features.json.JsonFeature
import com.example.azantime.shared.ApiUrl.BASE_URL
import io.ktor.client.features.json.serializer.KotlinxSerializer

class NetworkApi {
    private val httpClient = HttpClient {
        install(JsonFeature) {
            val json = Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun getAllLaunches(query:String): AzanEntity {
        return httpClient.get(BASE_URL+query)
    }

}

