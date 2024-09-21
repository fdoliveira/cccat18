package com.example.domain

data class CarPlate(val value: String) {
    init {
        if (!value.matches(Regex("^[A-Z]{3}[0-9]{4}$"))) {
            throw Exception("Invalid car plate")
        }
    }
}