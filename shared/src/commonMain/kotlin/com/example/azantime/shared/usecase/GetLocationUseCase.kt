package com.example.azantime.shared.usecase

import com.example.azantime.shared.di.Injector
import com.example.azantime.shared.entity.LocationEntity
import com.example.azantime.shared.repository.LocationRepository
import org.kodein.di.instance


class GetLocationUseCase : UseCase<UseCase.None, LocationEntity> {

    private val locationRepository: LocationRepository by Injector.di.instance()

    override suspend fun execute(input: UseCase.None): LocationEntity {
        return locationRepository.getCurrentLocation()
    }
}