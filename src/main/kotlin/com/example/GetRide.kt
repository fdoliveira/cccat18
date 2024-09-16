package com.example

class GetRide(val rideDAO: RideDAO) {
    fun execute(rideId: String): Ride {
        return rideDAO.getRideById(rideId) ?: throw Exception("Ride not found")
    }
}