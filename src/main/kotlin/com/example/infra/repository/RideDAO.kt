package com.example.infra.repository

import com.example.domain.Ride

interface RideDAO {
    fun getRideById(rideId: String): Ride?
    fun getUncompletedRideByPassengerId(passengerId: String): Ride?
    fun saveRide(ride: Ride): String
}