package com.example.app.usecase.account

import com.example.domain.Account
import com.example.infra.repository.AccountRepository
import com.example.infra.repository.AccountRepositoryDatabase

class GetAccount {
    private val accountRepository: AccountRepository by lazy { AccountRepositoryDatabase() }

    fun execute(accountId: String): Account {
        return accountRepository.getAccountById(accountId) ?: throw Exception("Account not found")
    }
}