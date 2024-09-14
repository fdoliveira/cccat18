package com.example

import kotlin.test.Test
import kotlin.test.assertFailsWith

class SignupTest {

    @Test
    fun givenValidAccount_whenCallSignup_thenCreateAccount() {
        // given
        val account = Account(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        // when
        val response = signup(account)
        // then
        assert(response.accountId.isNotEmpty())
    }

    @Test
    fun givenAnInvalidName_whenCallSignup_thenReturnInvalidNameException() {
        // given
        val account = Account(
            name = "John",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        // when
        val exception = assertFailsWith<Exception> {
            signup(account)
        }
        // then
        assert(exception.message == "Invalid name")
    }

    @Test
    fun givenAnInvalidEmail_whenCallSignup_thenReturnInvalidEmailException() {
        // given
        val account = Account(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}",
            isPassenger = true,
            password = "123456"
        )
        // when
        val exception = assertFailsWith<Exception> {
            signup(account)
        }
        // then
        assert(exception.message == "Invalid email")
    }

    @Test
    fun givenAnInvalidCpf_whenCallSignup_thenReturnInvalidCpfException() {
        // given
        val account = Account(
            name = "John Doe",
            cpf = "1746326905",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        // when
        val exception = assertFailsWith<Exception> {
            signup(account)
        }
        // then
        assert(exception.message == "Invalid cpf")
    }

    @Test
    fun givenAnInvalidCarPlate_whenCallSignup_thenReturnInvalidCarPlateException() {
        //given
        val account = Account(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isDriver = true,
            carPlate = "1BC1234",
            password = "123456"
        )
        // when
        val exception = assertFailsWith<Exception> {
            signup(account)
        }
        // then
        assert(exception.message == "Invalid car plate")
    }
}