package com.example.azantime.shared.repository

import com.example.azantime.shared.entity.LocationEntity


interface LocationRepository {
    suspend fun getCurrentLocation(): LocationEntity
}