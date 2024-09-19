package com.example.app.usecase

import com.example.domain.Account
import com.example.infra.repository.AccountDAO
import com.example.infra.gateway.MailerGateway
import com.example.infra.account.model.AccountRequest
import com.example.infra.account.model.CreateAccountResponse

class Signup(val accountDAO: AccountDAO, val mailerGateway: MailerGateway) {
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
        accountDAO.getAccountByEmail(input.email)?.let { throw Exception("Duplicated account") }
        val savedAccountId = accountDAO.saveAccount(account)
        mailerGateway.send(input.email, "Welcome!", "...")
        return CreateAccountResponse(savedAccountId)
    }
}