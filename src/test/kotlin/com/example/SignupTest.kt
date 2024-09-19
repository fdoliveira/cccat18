package com.example

import com.example.app.usecase.Signup
import com.example.infra.account.model.AccountRequest
import com.example.infra.gateway.MailerGatewayMemory
import com.example.infra.repository.AccountDAOPgsql
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class SignupTest {
    private lateinit var signup: Signup

    @BeforeTest
    fun setup() {
        signup = Signup(AccountDAOPgsql(), MailerGatewayMemory())
    }

    @Test
    fun givenValidPassenger_whenCallSignup_thenCreateAccount() {
        // given
        val account = AccountRequest(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        // when
        val response = signup.execute(account)
        // then
        assert(response.accountId.isNotEmpty())
    }

    @Test
    fun givenPassengerWithAnInvalidName_whenCallSignup_thenReturnInvalidNameException() {
        // given
        val account = AccountRequest(
            name = "John",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        // when
        val exception = assertFailsWith<Exception> {
            signup.execute(account)
        }
        // then
        assert(exception.message == "Invalid name")
    }

    @Test
    fun givenPassengerWithAnInvalidEmail_whenCallSignup_thenReturnInvalidEmailException() {
        // given
        val account = AccountRequest(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}",
            isPassenger = true,
            password = "123456"
        )
        // when
        val exception = assertFailsWith<Exception> {
            signup.execute(account)
        }
        // then
        assert(exception.message == "Invalid email")
    }

    @Test
    fun givenPassengerWithAnInvalidCpf_whenCallSignup_thenReturnInvalidCpfException() {
        // given
        val account = AccountRequest(
            name = "John Doe",
            cpf = "1746326905",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        // when
        val exception = assertFailsWith<Exception> {
            signup.execute(account)
        }
        // then
        assert(exception.message == "Invalid cpf")
    }

    @Test
    fun givenDriverWithAnInvalidCarPlate_whenCallSignup_thenReturnInvalidCarPlateException() {
        //given
        val account = AccountRequest(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isDriver = true,
            carPlate = "1BC1234",
            password = "123456"
        )
        // when
        val exception = assertFailsWith<Exception> {
            signup.execute(account)
        }
        // then
        assert(exception.message == "Invalid car plate")
    }

    @Test
    fun givenValidPassenger_whenCallSignup_thenCreateAccountWithStub() {
        // TODO: Implement test with stub
    }

    @Test
    fun givenValidPassenger_whenCallSignup_thenCreateAccountWithSpy() {
        // TODO: Implement test with spy
    }

    @Test
    fun givenValidPassenger_whenCallSignup_thenCreateAccountWithMock() {
        // TODO: Implement test with mock

//        val mailerMock = mockk<MailerGatewayMemory>()
//        val accountMock = mockk<AccountDAOMemory>()
//        val signupMock = Signup(accountMock, mailerMock)
//        // given
//        val account = Account(
//            name = "John Doe",
//            cpf = "17463269051",
//            email = "john.doe${Math.random()}@gmail.com",
//            isPassenger = true,
//            password = "123456"
//        )
//        // when
//        val response = signupMock.execute(account)
//        // then
//        assert(response.accountId.isNotEmpty())
//        verify(exactly = 1) { mailerMock.send(account.email, "Welcome!", "...") }
    }
}