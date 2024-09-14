package com.example

import java.util.UUID
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class GetAccountTest {
    private lateinit var signup: Signup
    private lateinit var getAccount: GetAccount
    private lateinit var accountDAO: AccountDAO

    @BeforeTest
    fun setup() {
        accountDAO = AccountDAOMemory()
        signup = Signup(accountDAO, MailerGatewayMemory())
        getAccount = GetAccount(accountDAO)
    }

    @Test
    fun givenValidAccountId_whenCallGetAccount_thenReturnSavedAccount() {
        // given
        val account = Account(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        val response = signup.execute(account)
        val accountId = response.accountId
        // when
        val savedAccount = getAccount.execute(accountId)
        // then
        assertEquals(savedAccount.accountId, accountId)
        assertEquals(savedAccount.name, account.name)
        assertEquals(savedAccount.email, account.email)
        assertEquals(savedAccount.cpf, account.cpf)
        assertEquals(savedAccount.isPassenger, account.isPassenger)
        assertEquals(savedAccount.isDriver, account.isDriver)
        assertEquals(savedAccount.carPlate, account.carPlate)
    }

    @Test
    fun givenInvalidAccountId_whenCallGetAccount_thenThrowException() {
        // given
        val invalidAccountId = UUID.randomUUID().toString()
        // when
        val exception = assertFailsWith<Exception> {
            getAccount.execute(invalidAccountId)
        }
        // then
        assertTrue(exception.message == "Account not found")
    }

}