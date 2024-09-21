package com.example.app.usecase.ride

import com.example.app.usecase.account.AccountOutput
import com.example.domain.Ride

data class RideOutput(
    val rideId: String,
    val passenger: AccountOutput,
    val driver: AccountOutput?,
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
        fun from(ride: Ride, passenger: AccountOutput, driver: AccountOutput?): RideOutput {
            return RideOutput(
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
