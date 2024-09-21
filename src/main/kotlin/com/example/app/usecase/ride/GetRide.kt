package com.example.app.usecase.ride

import com.example.app.usecase.account.GetAccountOutput
import com.example.infra.repository.AccountRepository
import com.example.infra.repository.AccountRepositoryDatabase
import com.example.infra.repository.RideRepository
import com.example.infra.repository.RideRepositoryDatabase

class GetRide {
    private val rideRepository: RideRepository by lazy { RideRepositoryDatabase() }
    private val accountRepository: AccountRepository by lazy { AccountRepositoryDatabase() }

    fun execute(rideId: String): GetRideOutput {
        val ride = rideRepository.getRideById(rideId) ?: throw Exception("Ride not found")
        val passenger = accountRepository.getAccountById(ride.getPassengerId()) ?: throw Exception("Passenger not found")
        val passengerOutput = GetAccountOutput.from(passenger)
        var driverOutput: GetAccountOutput? = null
        if (ride.getDriverId() != null) {
            driverOutput = accountRepository.getAccountById(ride.getDriverId()!!).let {
                if (it != null) GetAccountOutput.from(it) else null
            }
        }
        return GetRideOutput.from(ride, passengerOutput, driverOutput)
    }
}