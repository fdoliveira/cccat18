package com.example.infra.repository

import com.example.domain.Account

interface AccountRepository {
    fun getAccountByEmail(email: String): Account?
    fun getAccountById(accountId: String): Account?
    fun saveAccount(account: Account): String
}