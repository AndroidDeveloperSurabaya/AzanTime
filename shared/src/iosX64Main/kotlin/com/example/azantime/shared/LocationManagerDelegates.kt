package com.example.azantime.shared

import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.darwin.NSObject


class LocationManagerDelegates(private val onLocationUpdate: (List<CLLocation>) -> Unit)
    : NSObject(), CLLocationManagerDelegateProtocol {

    private var locations: List<CLLocation> = listOf<CLLocation>()

    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        @Suppress("UNCHECKED_CAST")
        locations = didUpdateLocations as List<CLLocation>
        onLocationUpdate(locations)
    }

}