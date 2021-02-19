package com.example.azantime.shared.repository

import com.example.azantime.shared.LocationManagerDelegates
import com.example.azantime.shared.entity.LocationEntity
import kotlinx.cinterop.useContents
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLLocationAccuracyBest
import kotlin.coroutines.resume

class NSSLocationRepository : LocationRepository {

    private val locationManager: CLLocationManager by lazy { CLLocationManager() }
    private var runner: Boolean = true

    override suspend fun getCurrentLocation(): LocationEntity {
        return suspendCancellableCoroutine { continuation ->
            locationManager.setDelegate(LocationManagerDelegates { locations ->
                locationManager.stopUpdatingLocation()

                locations.lastOrNull()?.coordinate?.useContents {
                    if (runner) {
                        continuation.resume(LocationEntity(latitude, longitude))
                        runner = false
                    }
                }
            })
            locationManager.desiredAccuracy = kCLLocationAccuracyBest
            locationManager.requestWhenInUseAuthorization()
            locationManager.startUpdatingLocation()
        }
    }
}