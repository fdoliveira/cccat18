package com.example.infra.account.model

import com.example.app.usecase.account.SignupOutput
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignupResponse(@SerialName("account_id") val accountId: String) {
    companion object {
        fun from(signupOutput: SignupOutput): SignupResponse {
            return SignupResponse(
                accountId = signupOutput.accountId
            )
        }
    }
}


