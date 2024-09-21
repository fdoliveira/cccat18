package com.example

import com.example.app.usecase.ride.RequestRide
import com.example.app.usecase.ride.GetRide
import com.example.app.usecase.account.Signup
import com.example.app.usecase.account.SignupCommand
import com.example.app.usecase.ride.RequestRideCommand
import com.example.infra.di.repositoryModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RequestRideTest: KoinTest {
    private lateinit var getRide: GetRide
    private lateinit var requestRide: RequestRide

    @BeforeTest
    fun setup() {
        getRide = GetRide()
        requestRide = RequestRide()
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
    fun givenValidRide_whenCallRequestRide_thenCreateRide() {
        // given
        val signupCommand = SignupCommand(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        val accountResponse = Signup().execute(signupCommand)
        val requestRideCommand = RequestRideCommand(
            passengerId = accountResponse.accountId,
            fromLat = -6.3637562,
            fromLong = -36.970218,
            toLat = -6.4599549,
            toLong = -37.0937225,
        )
        val response = requestRide.execute(requestRideCommand)
        // then
        assert(response.rideId.isNotEmpty())
    }

    @Test
    fun givenRideWithDriver_whenCallRequestRide_thenReturnNotPassengerException() {
        // given
        val signupCommand = SignupCommand(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isDriver = true,
            carPlate = "ABC1234",
            password = "123456"
        )
        val accountResponse = Signup().execute(signupCommand)
        val requestRideCommand = RequestRideCommand(
            passengerId = accountResponse.accountId,
            fromLat = -6.3637562,
            fromLong = -36.970218,
            toLat = -6.4599549,
            toLong = -37.0937225,
        )
        // when
        val exception = assertFailsWith<Exception> {
            requestRide.execute(requestRideCommand)
        }
        // then
        assertEquals("Not a Passenger", exception.message)
    }

    @Test
    fun givenRideWithPassengerWithRideInProgress_whenCallRequestRide_thenReturnPassengerWithRideInProgressException() {
        // given
        val signupCommand = SignupCommand(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isDriver = true,
            carPlate = "ABC1234",
            password = "123456"
        )
        val accountResponse = Signup().execute(signupCommand)
        val requestRideCommand = RequestRideCommand(
            passengerId = accountResponse.accountId,
            fromLat = -6.3637562,
            fromLong = -36.970218,
            toLat = -6.4599549,
            toLong = -37.0937225,
        )
        // when
        val exception = assertFailsWith<Exception> {
            requestRide.execute(requestRideCommand)
        }
        // then
        assertEquals("Not a Passenger", exception.message)
    }
}