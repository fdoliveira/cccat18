package com.example.app.usecase

import com.example.infra.repository.AccountDAO
import com.example.domain.Account

class GetAccount(val accountDAO: AccountDAO) {
    fun execute(accountId: String): Account {
        return accountDAO.getAccountById(accountId) ?: throw Exception("Account not found")
    }
}