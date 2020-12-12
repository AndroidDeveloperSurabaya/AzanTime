package com.example.azantime.shared

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

fun onLocationChanged(onChanged: (Location?) -> Unit) : LocationListener {
    return object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            onChanged.invoke(location)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }
    }
}