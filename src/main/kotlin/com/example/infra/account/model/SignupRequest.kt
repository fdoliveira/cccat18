package com.example.infra.account.model

import com.example.app.usecase.account.SignupCommand
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
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
) {
    fun toSignupCommand(): SignupCommand {
        return SignupCommand(
            name = name,
            email = email,
            cpf = cpf,
            isPassenger = isPassenger,
            isDriver = isDriver,
            carPlate = carPlate,
            password = password
        )
    }
}
