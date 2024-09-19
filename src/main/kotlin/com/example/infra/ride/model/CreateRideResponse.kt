package com.example.infra.ride.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRideResponse(
    @SerialName("ride_id")
    val rideId: String
)
