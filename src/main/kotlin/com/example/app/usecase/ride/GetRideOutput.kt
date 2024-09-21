package com.example.app.usecase.ride

import com.example.app.usecase.account.GetAccountOutput
import com.example.domain.Ride

data class GetRideOutput(
    val rideId: String,
    val passenger: GetAccountOutput,
    val driver: GetAccountOutput?,
    val status: String,
    val fare: Double?,
    val distance: Double?,
    val fromLat: Double,
    val fromLong: Double,
    val toLat: Double,
    val toLong: Double,
    val date: String
) {
    companion object {
        fun from(ride: Ride, passenger: GetAccountOutput, driver: GetAccountOutput?): GetRideOutput {
            return GetRideOutput(
                rideId = ride.getRideId()!!,
                passenger = passenger,
                driver = driver,
                status = ride.getStatus()!!,
                fare = ride.getFare(),
                distance = ride.getDistance(),
                fromLat = ride.getFromLat(),
                fromLong = ride.getFromLong(),
                toLat = ride.getToLat(),
                toLong = ride.getToLong(),
                date = ride.getDate().toString()
            )
        }
    }
}
