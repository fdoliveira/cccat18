package com.example.infra.ride.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestRideResponse(
    @SerialName("ride_id")
    val rideId: String
)
