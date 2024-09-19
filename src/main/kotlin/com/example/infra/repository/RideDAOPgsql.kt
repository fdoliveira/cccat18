package com.example.infra.repository

import com.example.domain.Ride
import com.example.infra.database.getKotlinxLocalDateTime
import com.example.infra.database.uuidToPgObject
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.sql.DriverManager
import java.sql.PreparedStatement

class RideDAOPgsql : RideDAO {
    private fun setInsertStmtParams(stmt: PreparedStatement, rideFromBody: Ride): String {
        val status = "requested"
        val currentMoment: Instant = Clock.System.now()
        val dateTime : LocalDateTime = currentMoment.toLocalDateTime(TimeZone.UTC)
        val ride = rideFromBody.from(status = status, date = dateTime)
        stmt.setObject(1, uuidToPgObject(ride.getRideId()!!))
        stmt.setObject(2, uuidToPgObject(ride.getPassengerId()))
        stmt.setString(3, ride.getStatus())
        stmt.setDouble(4, ride.getFromLat())
        stmt.setDouble(5, ride.getFromLong())
        stmt.setDouble(6, ride.getToLat())
        stmt.setDouble(7, ride.getToLong())
        return ride.getRideId()!!
    }

    override fun getRideById(rideId: String): Ride? {
        val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", "postgres", "123456")
        try {
            val statement = conn.prepareStatement("SELECT ride_id, passenger_id, driver_id, status, fare, distance, from_lat, from_long, to_lat, to_long, date FROM ccca.ride WHERE ride_id = ?")
            statement.setObject(1, uuidToPgObject(rideId))
            val resultSet = statement.executeQuery()
            if (resultSet.next()) {
                return Ride(
                    rideId = resultSet.getObject("ride_id").toString(),
                    passengerId = resultSet.getObject("passenger_id").toString(),
                    driverId = resultSet.getObject("driver_id")?.toString(),
                    status = resultSet.getString("status"),
                    fare = resultSet.getDouble("fare"),
                    distance = resultSet.getDouble("distance"),
                    fromLat = resultSet.getDouble("from_lat"),
                    fromLong = resultSet.getDouble("from_long"),
                    toLat = resultSet.getDouble("to_lat"),
                    toLong = resultSet.getDouble("to_long"),
                    date = resultSet.getKotlinxLocalDateTime("date")
                )
            }
            return null
        } finally {
            conn.close()
        }
    }

    override fun getUncompletedRideByPassengerId(passengerId: String): Ride? {
        val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", "postgres", "123456")
        try {
            val statement = conn.prepareStatement("SELECT ride_id, passenger_id, driver_id, status, fare, distance, from_lat, from_long, to_lat, to_long, date FROM ccca.ride WHERE passenger_id = ? AND status != 'completed'")
            statement.setObject(1, uuidToPgObject(passengerId))
            val resultSet = statement.executeQuery()
            if (resultSet.next()) {
                return Ride(
                    rideId = resultSet.getObject("ride_id").toString(),
                    passengerId = resultSet.getObject("passenger_id").toString(),
                    driverId = resultSet.getObject("driver_id")?.toString(),
                    status = resultSet.getString("status"),
                    fare = resultSet.getDouble("fare"),
                    distance = resultSet.getDouble("distance"),
                    fromLat = resultSet.getDouble("from_lat"),
                    fromLong = resultSet.getDouble("from_long"),
                    toLat = resultSet.getDouble("to_lat"),
                    toLong = resultSet.getDouble("to_long"),
                    date = resultSet.getObject("date", LocalDateTime::class.java)
                )
            }
            return null
        } finally {
            conn.close()
        }
    }

    override fun saveRide(ride: Ride): String {
        val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", "postgres", "123456")
        try {
            val stmt = conn.prepareStatement("INSERT INTO ccca.ride(ride_id, passenger_id, status, from_lat, from_long, to_lat, to_long, date) VALUES (?, ?, ?, ?, ?, ?, ?, now())")
            val id = setInsertStmtParams(stmt, ride)
            stmt.executeUpdate()
            return id
        } finally {
            conn.close()
        }
    }
}