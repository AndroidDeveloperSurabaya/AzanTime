package com.example.azantime.shared.di

import com.example.azantime.shared.api.AzanAPI
import com.example.azantime.shared.api.KtorAzanAPI
import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

private const val NETWORK_MODULE = "NETWORK_MODULE"

val networkModule = DI.Module(NETWORK_MODULE) {
    bind<HttpClient>() with singleton { createHttpClient() }
    bind<AzanAPI>() with singleton { KtorAzanAPI(instance()) }
}

private fun createHttpClient(): HttpClient = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(json = Json { ignoreUnknownKeys = true })
    }
}