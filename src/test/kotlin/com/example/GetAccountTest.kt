package com.example

import com.example.app.usecase.GetAccount
import com.example.app.usecase.Signup
import com.example.infra.account.model.AccountRequest
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
        val expectedAccount = AccountRequest(
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
        assertEquals(savedAccount.getAccountId(), accountId)
        assertEquals(savedAccount.getName(), expectedAccount.name)
        assertEquals(savedAccount.getEmail(), expectedAccount.email)
        assertEquals(savedAccount.getCPF(), expectedAccount.cpf)
        assertEquals(savedAccount.isPassenger(), true)
        assertEquals(savedAccount.isDriver(), false)
        assertNull(savedAccount.getCarPlate())
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