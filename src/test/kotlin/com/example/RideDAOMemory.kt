package com.example

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.UUID

class RideDAOMemory(
    private val rides: MutableMap<String, Ride> = mutableMapOf()
) : RideDAO {

    private fun setInsertStmtParams(rideFromBody: Ride): Ride {
        val id = UUID.randomUUID().toString()
        val status = "requested"
        val currentMoment: Instant = Clock.System.now()
        val dateTime : LocalDateTime = currentMoment.toLocalDateTime(TimeZone.UTC)
        return rideFromBody.copy(rideId = id, status = status, date = dateTime)
    }

    override fun getRideById(rideId: String): Ride? {
        return rides[rideId]
    }

    override fun getUncompletedRideByPassengerId(passengerId: String): Ride? {
        return rides.values.find { it.passengerId == passengerId && it.status != "completed" }
    }

    override fun saveRide(ride: Ride): String {
        val initRide = setInsertStmtParams(ride)
        rides[initRide.rideId!!] = initRide
        return initRide.rideId
    }
}