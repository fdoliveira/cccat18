package com.example.infra.ride.model

import com.example.app.usecase.ride.RideOutput
import com.example.infra.account.model.AccountResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRideResponse(
    @SerialName("ride_id") val rideId: String? = null,
    val passenger: AccountResponse,
    val driver: AccountResponse? = null,
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
        fun from(ride: RideOutput): GetRideResponse {
            return GetRideResponse(
                rideId = ride.rideId,
                passenger = AccountResponse.from(ride.passenger),
                driver = if (ride.driver != null) AccountResponse.from(ride.driver) else null,
                status = ride.status,
                fare = ride.fare,
                distance = ride.distance,
                fromLat = ride.fromLat,
                fromLong = ride.fromLong,
                toLat = ride.toLat,
                toLong = ride.toLong,
                date = ride.date,
            )
        }
    }
}
