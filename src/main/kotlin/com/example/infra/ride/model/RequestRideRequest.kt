package com.example.infra.ride.model

import com.example.app.usecase.ride.RequestRideCommand
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestRideRequest(
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
) {
    fun toRequestRideCommand(): RequestRideCommand {
        return RequestRideCommand(
            passengerId = this.passengerId,
            fromLat = this.fromLat,
            fromLong = this.fromLong,
            toLat = this.toLat,
            toLong = this.toLong
        )
    }
}