package com.example.azantime.shared.entity

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.internal.*

@Serializable
data class AzanEntity(
	val code: Long? = null,
	val status: String? = null,
	val data: Data? = null
)

@Serializable
data class Data(
	val timings: Timings? = null,
	val date: Date? = null,
	val meta: Meta? = null
)

@Serializable
data class Date(
	val readable: String? = null,
	val timestamp: String? = null,
	val hijri: Gregorian? = null,
	val gregorian: Gregorian? = null
)

@Serializable
data class Gregorian(
	val date: String? = null,
	val format: String? = null,
	val day: String? = null,
	val weekday: Weekday? = null,
	val month: Month? = null,
	val year: String? = null,
	val designation: Designation? = null,
	val holidays: JsonArray? = null
)

@Serializable
data class Designation(
	val abbreviated: String? = null,
	val expanded: String? = null
)

@Serializable
data class Month(
	val number: Long? = null,
	val en: String? = null
)

@Serializable
data class Weekday(
		val en: String? = null
)

@Serializable
data class Meta(
	val latitude: Double? = null,
	val longitude: Double? = null,
	val timezone: String? = null,
	val method: Method? = null,
	val latitudeAdjustmentMethod: String? = null,
	val midnightMode: String? = null,
	val school: String? = null,
	val offset: Map<String, Long>? = null
)

@Serializable
data class Method(
	val id: Long? = null,
	val name: String? = null,
	val params: Params? = null
)

@Serializable
data class Params(
	@SerialName("Fajr")
	val fajr: Double? = null,

	@SerialName("Isha")
	val isha: Double? = null
)

@Serializable
data class Timings(
	@SerialName("Fajr")
	val fajr: String? = null,

	@SerialName("Sunrise")
	val sunrise: String? = null,

	@SerialName("Dhuhr")
	val dhuhr: String? = null,

	@SerialName("Asr")
	val asr: String? = null,

	@SerialName("Sunset")
	val sunset: String? = null,

	@SerialName("Maghrib")
	val maghrib: String? = null,

	@SerialName("Isha")
	val isha: String? = null,

	@SerialName("Imsak")
	val imsak: String? = null,

	@SerialName("Midnight")
	val midnight: String? = null
)
