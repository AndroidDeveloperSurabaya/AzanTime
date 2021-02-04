package com.example.azantime.shared.repository

actual object RepositoryFactory {

    actual fun createLocationRepository(): LocationRepository {
        return NSSLocationRepository()
    }
}