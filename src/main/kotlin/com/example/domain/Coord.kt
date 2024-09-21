package com.example.domain

data class Coord(
    val lat: Double,
    val long: Double
) {
    init {
        if (lat < -90 || lat > 90) throw Exception("Invalid latitude")
        if (long < -180 || long > 180) throw Exception("Invalid longitude")
    }
}