package com.example.app.usecase.ride

data class RequestRideCommand(
    val passengerId: String,
    val fromLat: Double,
    val fromLong: Double,
    val toLat: Double,
    val toLong: Double
)