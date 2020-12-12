package com.example.azantime.shared.usecase

import com.example.azantime.shared.api.AzanAPI
import com.example.azantime.shared.di.Injector
import com.example.azantime.shared.entity.AzanEntity
import org.kodein.di.instance

class GetPrayerTimesUseCase : UseCase<GetPrayerTimesUseCase.Input, AzanEntity> {

    private val api: AzanAPI by Injector.di.instance()

    override suspend fun execute(input: Input): AzanEntity {
        return api.getPrayerTimes(input.timestamp, input.latitude, input.longitude)
    }

    data class Input(
        val timestamp: Long,
        val latitude: Double,
        val longitude: Double
    )

}