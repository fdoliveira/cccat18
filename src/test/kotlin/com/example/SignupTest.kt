package com.example

import com.example.app.usecase.account.Signup
import com.example.app.usecase.account.SignupCommand
import com.example.infra.di.repositoryModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class SignupTest: KoinTest {
    private lateinit var signup: Signup

    @BeforeTest
    fun setup() {
        signup = Signup()
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
    fun givenValidPassenger_whenCallSignup_thenCreateAccount() {
        // given
        val signupCommand = SignupCommand(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        // when
        val response = signup.execute(signupCommand)
        // then
        assert(response.accountId.isNotEmpty())
    }

    @Test
    fun givenPassengerWithAnInvalidName_whenCallSignup_thenReturnInvalidNameException() {
        // given
        val signupCommand = SignupCommand(
            name = "John",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        // when
        val exception = assertFailsWith<Exception> {
            signup.execute(signupCommand)
        }
        // then
        assert(exception.message == "Invalid name")
    }

    @Test
    fun givenPassengerWithAnInvalidEmail_whenCallSignup_thenReturnInvalidEmailException() {
        // given
        val signupCommand = SignupCommand(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}",
            isPassenger = true,
            password = "123456"
        )
        // when
        val exception = assertFailsWith<Exception> {
            signup.execute(signupCommand)
        }
        // then
        assert(exception.message == "Invalid email")
    }

    @Test
    fun givenPassengerWithAnInvalidCpf_whenCallSignup_thenReturnInvalidCpfException() {
        // given
        val signupCommand = SignupCommand(
            name = "John Doe",
            cpf = "1746326905",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        // when
        val exception = assertFailsWith<Exception> {
            signup.execute(signupCommand)
        }
        // then
        assert(exception.message == "Invalid cpf")
    }

    @Test
    fun givenDriverWithAnInvalidCarPlate_whenCallSignup_thenReturnInvalidCarPlateException() {
        //given
        val signupCommand = SignupCommand(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isDriver = true,
            carPlate = "1BC1234",
            password = "123456"
        )
        // when
        val exception = assertFailsWith<Exception> {
            signup.execute(signupCommand)
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
    }
}