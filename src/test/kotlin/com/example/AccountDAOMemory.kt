package com.example

import java.util.UUID

class AccountDAOMemory(
    private val accounts: MutableMap<String, Account> = mutableMapOf()
) : AccountDAO {

    override fun getAccountById(accountId: String): Account? {
        return accounts[accountId]
    }

    override fun getAccountByEmail(email: String): Account? {
        return accounts.values.find { it.email == email }
    }

    override fun saveAccount(account: Account): String {
        val id = UUID.randomUUID().toString()
        accounts[id] = account.copy(accountId = id)
        return id
    }
}