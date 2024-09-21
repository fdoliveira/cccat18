package com.example

import com.example.app.usecase.account.GetAccount
import com.example.app.usecase.account.Signup
import com.example.app.usecase.account.SignupCommand
import com.example.infra.di.repositoryModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import java.util.UUID
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GetAccountTest: KoinTest {
    private lateinit var signup: Signup
    private lateinit var getAccount: GetAccount

    @BeforeTest
    fun setup() {
        signup = Signup()
        getAccount = GetAccount()
        stopKoin() // to remove 'A Koin Application has already been started'
        startKoin {
            modules(repositoryModule)
        }
    }

    @AfterTest
    fun cleanUp() {
        stopKoin()
    }

    @Test
    fun givenValidAccountId_whenCallGetAccount_thenReturnSavedAccount() {
        // given
        val expectedAccount = SignupCommand(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        val response = signup.execute(expectedAccount)
        val accountId = response.accountId
        // when
        val savedAccount = getAccount.execute(accountId)
        // then
        assertEquals(savedAccount.accountId, accountId)
        assertEquals(savedAccount.name, expectedAccount.name)
        assertEquals(savedAccount.email, expectedAccount.email)
        assertEquals(savedAccount.cpf, expectedAccount.cpf)
        assertEquals(savedAccount.isPassenger, true)
        assertEquals(savedAccount.isDriver, false)
        assertNull(savedAccount.carPlate)
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