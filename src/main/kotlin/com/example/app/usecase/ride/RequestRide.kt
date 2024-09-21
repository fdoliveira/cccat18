package com.example.app.usecase.ride

import com.example.domain.Ride
import com.example.infra.repository.AccountRepository
import com.example.infra.repository.AccountRepositoryDatabase
import com.example.infra.repository.RideRepository
import com.example.infra.repository.RideRepositoryDatabase
import com.example.infra.ride.model.RequestRideResponse

class RequestRide() {
    private val rideRepository: RideRepository by lazy { RideRepositoryDatabase() }
    private val accountRepository: AccountRepository by lazy { AccountRepositoryDatabase() }

    fun execute(input: RequestRideCommand): RequestRideResponse {
        val ride = Ride.create(
            passengerId = input.passengerId,
            fromLat = input.fromLat,
            fromLong = input.fromLong,
            toLat = input.toLat,
            toLong = input.toLong,
        )
        val account = accountRepository.getAccountById(input.passengerId)
        if (account == null) { throw Exception("Account not found") }
        if (!account.isPassenger()) { throw Exception("Not a Passenger") }
        if (rideRepository.getUncompletedRideByPassengerId(input.passengerId) != null) { throw Exception("This Passenger have a Ride in progress") }
        val rideId = rideRepository.saveRide(ride)
        return RequestRideResponse(rideId)
    }
}