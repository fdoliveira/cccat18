package com.example.infra.ride.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RideRequest(
    @SerialName("passenger_id")
    val passengerId: String,
    @SerialName("from_lat")
    val fromLat: Double,
    @SerialName("from_long")
    val fromLong: Double,
    @SerialName("to_lat")
    val toLat: Double,
    @SerialName("to_long")
    val toLong: Double
)