package com.example

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.util.UUID

class RideDAOPgsql : RideDAO {
    private fun setInsertStmtParams(stmt: PreparedStatement, rideFromBody: Ride): String {
        val id = UUID.randomUUID().toString()
        val status = "requested"
        val currentMoment: Instant = Clock.System.now()
        val dateTime : LocalDateTime = currentMoment.toLocalDateTime(TimeZone.UTC)
        val ride = rideFromBody.copy(rideId = id, status = status, date = dateTime)
        stmt.setObject(1, idToPgObject(ride.rideId!!))
        stmt.setString(2, ride.passengerId)
        if (ride.driverId == null) stmt.setNull(3, java.sql.Types.OTHER) else stmt.setObject(3, ride.driverId)
        stmt.setString(4, ride.status)
        if (ride.fare == null) stmt.setNull(5, java.sql.Types.DOUBLE) else stmt.setDouble(5, ride.fare)
        if (ride.distance == null) stmt.setNull(6, java.sql.Types.DOUBLE) else stmt.setDouble(6, ride.distance)
        stmt.setDouble(7, ride.fromLat)
        stmt.setDouble(8, ride.fromLong)
        stmt.setDouble(9, ride.toLat)
        stmt.setDouble(10, ride.toLong)
        stmt.setObject(11, ride.date)
        return id
    }

    override fun getRideById(rideId: String): Ride? {
        val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", "postgres", "123456")
        try {
            val statement = conn.prepareStatement("SELECT ride_id, passenger_id, driver_id, status, fare, distance, from_lat, from_long, to_lat, to_long, date FROM ccca.ride WHERE ride_id = ?")
            statement.setObject(1, idToPgObject(rideId))
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

    override fun getUncompletedRideByPassengerId(passengerId: String): Ride? {
        val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", "postgres", "123456")
        try {
            val statement = conn.prepareStatement("SELECT ride_id, passenger_id, driver_id, status, fare, distance, from_lat, from_long, to_lat, to_long, date FROM ccca.ride WHERE passenger_id = ? AND status != 'completed'")
            statement.setObject(1, idToPgObject(passengerId))
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
            val stmt = conn.prepareStatement("INSERT INTO ccca.ride(ride_id, passenger_id, driver_id, cpf, is_passenger, is_driver, car_plate, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
            val id = setInsertStmtParams(stmt, ride)
            stmt.executeUpdate()
            return id
        } finally {
            conn.close()
        }
    }
}