package com.example.infra.account.model

import com.example.app.usecase.account.GetAccountOutput
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAccountResponse(
    @SerialName("account_id")
    val accountId: String,
    val name: String,
    val email: String,
    val cpf: String,
    @SerialName("is_passenger")
    val isPassenger: Boolean = false,
    @SerialName("is_driver")
    val isDriver: Boolean = false,
    @SerialName("car_plate")
    val carPlate: String? = null,
) {
    companion object {
        fun from(account: GetAccountOutput): GetAccountResponse {
            return GetAccountResponse(
                accountId = account.accountId,
                name = account.name,
                email = account.email,
                cpf = account.cpf,
                isPassenger = account.isPassenger,
                isDriver = account.isDriver,
                carPlate = account.carPlate,
            )
        }
    }
}
