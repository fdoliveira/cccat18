package com.example.infra.repository

import com.example.domain.Account

interface AccountDAO {
    fun getAccountById(accountId: String): Account?
    fun getAccountByEmail(email: String): Account?
    fun saveAccount(account: Account): String
}