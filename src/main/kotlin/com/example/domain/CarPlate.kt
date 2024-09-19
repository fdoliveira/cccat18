package com.example.domain

import kotlinx.serialization.Serializable

@Serializable
data class CarPlate(val value: String) {
    init {
        if (!value.matches(Regex("^[A-Z]{3}[0-9]{4}$"))) {
            throw Exception("Invalid car plate")
        }
    }
}
