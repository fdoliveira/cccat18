package com.example.infra.ride.model

import com.example.domain.Ride
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRideResponse(
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
    val date: String? = null,
) {
    companion object {
        fun from(ride: Ride): GetRideResponse {
            return GetRideResponse(
                rideId = ride.getRideId(),
                passengerId = ride.getPassengerId(),
                driverId = ride.getDriverId(),
                status = ride.getStatus(),
                fare = ride.getFare(),
                distance = ride.getDistance(),
                fromLat = ride.getFromLat(),
                fromLong = ride.getFromLong(),
                toLat = ride.getToLat(),
                toLong = ride.getToLong(),
                date = ride.getDate().toString(),
            )
        }
    }
}
