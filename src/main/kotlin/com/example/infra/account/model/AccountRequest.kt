package com.example.infra.account.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountRequest(
    val name: String,
    val email: String,
    val cpf: String,
    @SerialName("is_passenger")
    val isPassenger: Boolean = false,
    @SerialName("is_driver")
    val isDriver: Boolean = false,
    @SerialName("car_plate")
    val carPlate: String? = null,
    val password: String
)
