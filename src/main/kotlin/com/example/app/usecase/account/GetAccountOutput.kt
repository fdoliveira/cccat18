package com.example.app.usecase.account

import com.example.domain.Account

data class GetAccountOutput(
    val accountId: String,
    val name: String,
    val email: String,
    val cpf: String,
    val isPassenger: Boolean = false,
    val isDriver: Boolean = false,
    val carPlate: String? = null
) {
    companion object {
        fun from(account: Account): GetAccountOutput {
            return GetAccountOutput(
                accountId = account.getAccountId()!!,
                name = account.getName(),
                email = account.getEmail(),
                cpf = account.getCPF(),
                isPassenger = account.isPassenger(),
                isDriver = account.isDriver(),
                carPlate = account.getCarPlate()
            )
        }
    }
}
