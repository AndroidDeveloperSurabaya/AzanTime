package com.example.azantime.shared.repository

import com.example.azantime.shared.LocationManagerDelegates
import com.example.azantime.shared.entity.LocationEntity
import kotlinx.cinterop.useContents
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLLocationAccuracyBest
import kotlin.coroutines.resume


class NSSLocationRepository : LocationRepository {

    private val locationManager: CLLocationManager by lazy { CLLocationManager() }

    override suspend fun getCurrentLocation(): LocationEntity {
        return suspendCancellableCoroutine { continuation ->
            locationManager.setDelegate(LocationManagerDelegates { locations ->
                locationManager.stopUpdatingLocation()
                locations.lastOrNull()?.coordinate?.useContents {
                    continuation.resume(LocationEntity(latitude, longitude))
                }
            })
            locationManager.desiredAccuracy = kCLLocationAccuracyBest
            locationManager.requestWhenInUseAuthorization()
            locationManager.startUpdatingLocation()
        }
    }
}