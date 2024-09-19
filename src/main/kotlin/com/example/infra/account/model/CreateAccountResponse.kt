package com.example.infra.account.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountResponse(@SerialName("account_id") val accountId: String)


