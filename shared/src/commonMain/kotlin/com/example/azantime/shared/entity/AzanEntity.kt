package com.example.azantime.shared.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AzanEntity(

    @SerialName("code")
	val code: Int = 0,

    @SerialName("data")
	val data: Data? =null,

    @SerialName("status")
	val status: String? = null
)

@Serializable
data class Timings(

	@SerialName("Sunset")
	val sunset: String? = null,

	@SerialName("Asr")
	val asr: String? = null,

	@SerialName("Isha")
	val isha: String? = null,

	@SerialName("Fajr")
	val fajr: String? = null,

	@SerialName("Dhuhr")
	val dhuhr: String? = null,

	@SerialName("Maghrib")
	val maghrib: String? = null,

	@SerialName("Sunrise")
	val sunrise: String? = null,

	@SerialName("Midnight")
	val midnight: String? = null,

	@SerialName("Imsak")
	val imsak: String? = null
)

@Serializable
data class Params(

	@SerialName("Isha")
	val isha: Double? = null,

	@SerialName("Fajr")
	val fajr: Double? = null
)

@Serializable
data class Offset(

	@SerialName("Sunset")
	val sunset: Int? = null,

	@SerialName("Asr")
	val asr: Int? = null,

	@SerialName("Isha")
	val isha: Int? = null,

	@SerialName("Fajr")
	val fajr: Int? = null,

	@SerialName("Dhuhr")
	val dhuhr: Int? = null,

	@SerialName("Maghrib")
	val maghrib: Int? = null,

	@SerialName("Sunrise")
	val sunrise: Int? = null,

	@SerialName("Midnight")
	val midnight: Int? = null,

	@SerialName("Imsak")
	val imsak: Int? = null
)

@Serializable
data class Meta(

    @SerialName("method")
	val method: Method? = null,

    @SerialName("offset")
	val offset: Offset? = null,

    @SerialName("school")
	val school: String? = null,

    @SerialName("timezone")
	val timezone: String? = null,

    @SerialName("midnightMode")
	val midnightMode: String? = null,

    @SerialName("latitude")
	val latitude: Double? = null,

    @SerialName("longitude")
	val longitude: Double? = null,

    @SerialName("latitudeAdjustmentMethod")
	val latitudeAdjustmentMethod: String? = null
)

@Serializable
data class Data(

    @SerialName("date")
	val date: Date? = null,

    @SerialName("meta")
	val meta: Meta? = null,

    @SerialName("timings")
	val timings: Timings? = null
)

@Serializable
data class Date(

	@SerialName("readable")
	val readable: String? = null,

	@SerialName("hijri")
	val hijri: String? = null,

	@SerialName("gregorian")
	val gregorian: String? = null,

	@SerialName("timestamp")
	val timestamp: String? = null
)

@Serializable
data class Method(

	@SerialName("name")
	val name: String? = null,

	@SerialName("id")
	val id: Int? = null,

	@SerialName("params")
	val params: Params? = null
)
