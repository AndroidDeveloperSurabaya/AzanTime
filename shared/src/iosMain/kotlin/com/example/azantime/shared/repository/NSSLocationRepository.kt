package com.example.azantime.shared.repository

import com.example.azantime.shared.entity.LocationEntity


class NSSLocationRepository : LocationRepository {
    override suspend fun getCurrentLocation(): LocationEntity {
        val latitude = -7.2756141
        val longitude = -112.642642
        return LocationEntity(latitude, longitude)
    }
}