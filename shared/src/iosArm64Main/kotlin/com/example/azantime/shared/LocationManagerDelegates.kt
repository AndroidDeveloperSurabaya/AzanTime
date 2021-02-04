package com.example.azantime.shared

import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.Foundation.NSError
import platform.darwin.NSObject


class LocationManagerDelegates(private val onLocationUpdate: (List<CLLocation>, Int) -> Unit)
    : NSObject(), CLLocationManagerDelegateProtocol {

    private var locations: List<CLLocation> = listOf<CLLocation>()
    private var status: Int = 0

    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        @Suppress("UNCHECKED_CAST")
        locations = didUpdateLocations as List<CLLocation>
        onLocationUpdate(locations, status)
    }

    override fun locationManager(manager: CLLocationManager, didChangeAuthorizationStatus: CLAuthorizationStatus) {
        @Suppress("UNCHECKED_CAST")
        status = didChangeAuthorizationStatus as Int
        onLocationUpdate(locations, status)
    }

}