package com.example.app.usecase.ride

import com.example.app.usecase.account.AccountOutput
import com.example.infra.repository.AccountRepository
import com.example.infra.repository.AccountRepositoryDatabase
import com.example.infra.repository.RideRepository
import com.example.infra.repository.RideRepositoryDatabase

class GetRide {
    private val rideRepository: RideRepository by lazy { RideRepositoryDatabase() }
    private val accountRepository: AccountRepository by lazy { AccountRepositoryDatabase() }

    fun execute(rideId: String): RideOutput {
        val ride = rideRepository.getRideById(rideId) ?: throw Exception("Ride not found")
        val passenger = accountRepository.getAccountById(ride.getPassengerId()) ?: throw Exception("Passenger not found")
        val passengerOutput = AccountOutput.from(passenger)
        var driverOutput: AccountOutput? = null
        if (ride.getDriverId() != null) {
            driverOutput = accountRepository.getAccountById(ride.getDriverId()!!).let {
                if (it != null) AccountOutput.from(it) else null
            }
        }
        return RideOutput.from(ride, passengerOutput, driverOutput)
    }
}