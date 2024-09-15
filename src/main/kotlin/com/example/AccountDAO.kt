package com.example

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val accountId: String? = null,
    val name: String,
    val email: String,
    val cpf: String,
    @SerialName("is_passenger") val isPassenger: Boolean = false,
    @SerialName("is_driver") val isDriver: Boolean = false,
    @SerialName("car_plate") val carPlate: String? = null,
    val password: String
)

@Serializable
data class AccountResponse(@SerialName("account_id") val accountId: String)

interface AccountDAO {
    fun getAccountById(accountId: String): Account?
    fun getAccountByEmail(email: String): Account?
    fun saveAccount(account: Account): String
}