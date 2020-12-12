package com.example.azantime.shared.repository

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.location.LocationManager
import androidx.annotation.RequiresPermission
import com.example.azantime.shared.entity.LocationEntity
import com.example.azantime.shared.onLocationChanged
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class AndroidLocationRepository(context: Context) : LocationRepository {

    companion object {
        private const val MIN_TIME = 30_000L
        private const val MIN_DISTANCE = 5f
    }

    private val locationManager: LocationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @RequiresPermission(anyOf = [ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION])
    override suspend fun getCurrentLocation(): LocationEntity {
        return suspendCancellableCoroutine { continuation ->
            locationManager.resolveProvider()?.let { provider ->
                locationManager.requestLocationUpdates(
                    provider,
                    MIN_TIME,
                    MIN_DISTANCE,
                    onLocationChanged { location ->
                        location?.let {
                            continuation.resume(LocationEntity(it.latitude, it.longitude))
                        } ?: continuation.resumeWithException(Error("Failed get Location"))
                    })
            }
        }
    }

    private fun LocationManager.resolveProvider(): String? = when {
        LocationManager.NETWORK_PROVIDER in allProviders -> LocationManager.NETWORK_PROVIDER
        LocationManager.GPS_PROVIDER in allProviders -> LocationManager.GPS_PROVIDER
        else -> null
    }
}