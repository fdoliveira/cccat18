package com.example.domain

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AccountTest {
    private val expectedName = "John Doe"
    private val expectedCPF = "17463269051"
    private lateinit var expectedEmail: String
    val expectedPassword = "123456"

    @BeforeTest
    fun setup() {
        expectedEmail = "john.doe${Math.random()}@gmail.com"
    }

    @Test
    fun givenValidPassenger_whenCreateAccount_thenAccountIsCreated() {
        // Given - Setup
        // When
        val account = Account.create(
            name = expectedName,
            cpf = expectedCPF,
            email = expectedEmail,
            isPassenger = true,
            password = expectedPassword
        )
        // Then
        assertEquals(account.getName(), expectedName)
        assertEquals(account.getCPF(), expectedCPF)
        assertEquals(account.getEmail(), expectedEmail)
        assertEquals(account.isPassenger(), true)
        assertEquals(account.isDriver(), false)
        assertEquals(account.getCarPlate(), null)
        assertEquals(account.getPassword(), expectedPassword)
    }

    @Test
    fun givenPassengerWithInvalidName_whenCreateAccount_thenThrowInvalidNameException() {
        // Given
        val invalidName = "John"
        // When
        val exception = assertFailsWith<Exception> {
            Account.create(
                name = invalidName,
                email = expectedEmail,
                cpf = expectedCPF,
                isPassenger = true,
                password = expectedPassword
            )
        }
        // Then
        assertEquals(exception.message, "Invalid name")
    }

    @Test
    fun givenPassengerWithInvalidEmail_whenCreateAccount_thenThrowInvalidEmailException() {
        // Given
        val invalidEmail = "john.doe${Math.random()}"
        // When
        val exception = assertFailsWith<Exception> {
            Account.create(
                name = expectedName,
                email = invalidEmail,
                cpf = expectedCPF,
                isPassenger = true,
                password = expectedPassword
            )
        }
        // Then
        assertEquals(exception.message, "Invalid email")
    }

    @Test
    fun givenPassengerWithInvalidCpf_whenCreateAccount_thenThrowInvalidCpfException() {
        // Given
        val invalidCpf = "1746326905"
        // When
        val exception = assertFailsWith<Exception> {
            Account.create(
                name = expectedName,
                email = expectedEmail,
                cpf = invalidCpf,
                isPassenger = true,
                password = expectedPassword
            )
        }
        // Then
        assertEquals(exception.message, "Invalid cpf")
    }

    @Test
    fun givenDriverWithInvalidCarPlate_whenCreateAccount_thenThrowInvalidCarPlateException() {
        // Given
        val invalidCarPlate = "1BC1234"
        // When
        val exception = assertFailsWith<Exception> {
            Account.create(
                name = expectedName,
                email = expectedEmail,
                cpf = expectedCPF,
                isDriver = true,
                carPlate = invalidCarPlate,
                password = expectedPassword
            )
        }
        // Then
        assertEquals(exception.message, "Invalid car plate")
    }
}