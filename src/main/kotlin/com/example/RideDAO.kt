package com.example

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ride(
    @SerialName("ride_id") val rideId: String? = null,
    @SerialName("passenger_id") val passengerId: String,
    @SerialName("driver_id") val driverId: String? = null,
    val status: String? = null,
    val fare: Double? = null,
    val distance: Double? = null,
    @SerialName("from_lat") val fromLat: Double,
    @SerialName("from_long") val fromLong: Double,
    @SerialName("to_lat") val toLat: Double,
    @SerialName("to_long") val toLong: Double,
    val date: LocalDateTime? = null,
)

@Serializable
data class RideResponse(@SerialName("ride_id") val rideId: String)

interface RideDAO {
    fun getRideById(rideId: String): Ride?
    fun getUncompletedRideByPassengerId(passengerId: String): Ride?
    fun saveRide(ride: Ride): String

}