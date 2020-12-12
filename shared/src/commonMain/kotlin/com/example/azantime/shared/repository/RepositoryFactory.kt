package com.example.azantime.shared.repository


expect object RepositoryFactory {
    fun createLocationRepository(): LocationRepository
}