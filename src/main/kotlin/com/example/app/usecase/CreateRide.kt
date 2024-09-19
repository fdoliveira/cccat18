package com.example.app.usecase

import com.example.domain.Ride
import com.example.infra.repository.AccountDAO
import com.example.infra.repository.RideDAO
import com.example.infra.ride.model.CreateRideResponse
import com.example.infra.ride.model.RideRequest

class CreateRide(val rideDAO: RideDAO, val accountDAO: AccountDAO) {
    fun execute(input: RideRequest): CreateRideResponse {
        val ride = Ride.create(
            passengerId = input.passengerId,
            fromLat = input.fromLat,
            fromLong = input.fromLong,
            toLat = input.toLat,
            toLong = input.toLong,
        )
        val account = accountDAO.getAccountById(input.passengerId)
        if (account == null) { throw Exception("Account not found") }
        if (!account.isPassenger()) { throw Exception("Not a Passenger") }
        if (rideDAO.getUncompletedRideByPassengerId(input.passengerId) != null) { throw Exception("This Passenger have a Ride in progress") }
        val rideId = rideDAO.saveRide(ride)
        return CreateRideResponse(rideId)
    }
}