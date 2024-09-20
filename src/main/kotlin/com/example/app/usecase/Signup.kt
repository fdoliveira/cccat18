package com.example.app.usecase

import com.example.domain.Account
import com.example.infra.account.model.AccountRequest
import com.example.infra.account.model.CreateAccountResponse
import com.example.infra.gateway.MailerGateway
import com.example.infra.gateway.MailerGatewayMemory
import com.example.infra.repository.AccountRepository
import com.example.infra.repository.AccountRepositoryDatabase

class Signup {
    private val accountRepository: AccountRepository by lazy { AccountRepositoryDatabase() }
    private val mailerGateway: MailerGateway by lazy { MailerGatewayMemory() }

    fun execute(input: AccountRequest): CreateAccountResponse {
        val account = Account.create(
            name = input.name,
            cpf = input.cpf,
            email = input.email,
            isPassenger = input.isPassenger,
            isDriver = input.isDriver,
            password = input.password,
            carPlate = input.carPlate
        )
        accountRepository.getAccountByEmail(input.email)?.let { throw Exception("Duplicated account") }
        val savedAccountId = accountRepository.saveAccount(account)
        mailerGateway.send(input.email, "Welcome!", "...")
        return CreateAccountResponse(savedAccountId)
    }
}