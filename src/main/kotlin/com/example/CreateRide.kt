package com.example

class CreateRide(val rideDAO: RideDAO, val accountDAO: AccountDAO) {
    fun execute(input: Ride): RideResponse {
        // Preconditions Business Rules
        val account = accountDAO.getAccountById(input.passengerId)
        if (account == null) { throw Exception("Account not found") }
        if (!account.isPassenger) { throw Exception("Not a Passenger") }
        if (rideDAO.getUncompletedRideByPassengerId(input.passengerId) != null) { throw Exception("This Passenger have a Ride in progress") }
        // Save Ride
        val savedRide = rideDAO.saveRide(input)
        return RideResponse(savedRide)
    }
}