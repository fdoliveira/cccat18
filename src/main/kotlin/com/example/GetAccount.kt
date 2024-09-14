package com.example

class GetAccount(val accountDAO: AccountDAO) {
    fun execute(accountId: String): Account {
        return accountDAO.getAccountById(accountId) ?: throw Exception("Account not found")
    }
}