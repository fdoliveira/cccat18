package com.example.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.ZoneOffset
import java.util.Date

class Ride {
    private val rideId: UUID?
    private val passengerId: UUID
    private val driverId: UUID?
    private val status: String?
    private val fare: Double?
    private val distance: Double?
    private val fromLat: Double
    private val fromLong: Double
    private val toLat: Double
    private val toLong: Double
    private val date: LocalDateTime?

    constructor(rideId: String? = null,
                passengerId: String,
                driverId: String? = null,
                status: String? = null,
                fare: Double? = null,
                distance: Double? = null,
                fromLat: Double,
                fromLong: Double,
                toLat: Double,
                toLong: Double,
                date: LocalDateTime? = null) {
        this.rideId = rideId?.let { UUID(it) }
        this.passengerId = UUID(passengerId)
        this.driverId = driverId?.let { UUID(it) }
        this.status = status?.let { status }
        this.fare = fare?.let { fare }
        this.distance = distance?.let { distance }
        this.fromLat = fromLat
        this.fromLong = fromLong
        this.toLat = toLat
        this.toLong = toLong
        this.date = date?.let { date }
    }

    companion object {
        fun create(passengerId: String,
                   driverId: String? = null,
                   status: String? = null,
                   fare: Double? = null,
                   distance: Double? = null,
                   fromLat: Double,
                   fromLong: Double,
                   toLat: Double,
                   toLong: Double,
                   date: LocalDateTime? = null) = Ride(
            rideId = UUID.create().value,
            passengerId = passengerId,
            driverId = driverId,
            status = status,
            fare = fare,
            distance = distance,
            fromLat = fromLat,
            fromLong = fromLong,
            toLat = toLat,
            toLong = toLong,
            date = date
        )
    }

    fun from(rideId: String? = this.rideId?.value,
             passengerId: String = this.passengerId.value,
             driverId: String? = this.driverId?.value,
             status: String? = this.status,
             fare: Double? = this.fare,
             distance: Double? = this.distance,
             fromLat: Double = this.fromLat,
             fromLong: Double = this.fromLong,
             toLat: Double = this.toLat,
             toLong: Double = this.toLong,
             date: LocalDateTime? = this.date) = Ride(
        rideId = rideId,
        passengerId = passengerId,
        driverId = driverId,
        status = status,
        fare = fare,
        distance = distance,
        fromLat = fromLat,
        fromLong = fromLong,
        toLat = toLat,
        toLong = toLong,
        date = date
    )

    fun getRideId(): String? = rideId?.value
    fun getPassengerId(): String = passengerId.value
    fun getDriverId(): String? = driverId?.value
    fun getStatus(): String? = status
    fun getFare(): Double? = fare
    fun getDistance(): Double? = distance
    fun getFromLat(): Double = fromLat
    fun getFromLong(): Double = fromLong
    fun getToLat(): Double = toLat
    fun getToLong(): Double = toLong
    fun getDate(): Date? = date?.toJavaLocalDateTime()?.toInstant(ZoneOffset.UTC)?.let { Date.from(it) }
}