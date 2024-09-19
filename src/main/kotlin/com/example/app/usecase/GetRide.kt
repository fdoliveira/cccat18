package com.example.app.usecase

import com.example.domain.Ride
import com.example.infra.repository.RideDAO

class GetRide(val rideDAO: RideDAO) {
    fun execute(rideId: String): Ride {
        return rideDAO.getRideById(rideId) ?: throw Exception("Ride not found")
    }
}