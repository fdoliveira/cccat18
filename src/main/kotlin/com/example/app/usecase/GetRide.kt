package com.example.app.usecase

import com.example.domain.Ride
import com.example.infra.repository.RideRepository
import com.example.infra.repository.RideRepositoryDatabase

class GetRide {
    private val rideRepository: RideRepository by lazy { RideRepositoryDatabase() }

    fun execute(rideId: String): Ride {
        return rideRepository.getRideById(rideId) ?: throw Exception("Ride not found")
    }
}