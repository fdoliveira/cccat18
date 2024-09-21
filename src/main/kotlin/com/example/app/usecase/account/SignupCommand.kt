package com.example.app.usecase.account

data class SignupCommand(
    val name: String,
    val email: String,
    val cpf: String,
    val isPassenger: Boolean = false,
    val isDriver: Boolean = false,
    val carPlate: String? = null,
    val password: String
)