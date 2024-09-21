package com.example

import com.example.app.usecase.RequestRide
import com.example.app.usecase.GetRide
import com.example.app.usecase.Signup
import com.example.infra.account.model.AccountRequest
import com.example.infra.di.repositoryModule
import com.example.infra.ride.model.RideRequest
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
        val accountRequest = AccountRequest(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        val accountResponse = Signup().execute(accountRequest)
        val rideRequest = RideRequest(
            passengerId = accountResponse.accountId,
            fromLat = -6.3637562,
            fromLong = -36.970218,
            toLat = -6.4599549,
            toLong = -37.0937225,
        )
        val response = requestRide.execute(rideRequest)
        // then
        assert(response.rideId.isNotEmpty())
    }

    @Test
    fun givenRideWithDriver_whenCallRequestRide_thenReturnNotPassengerException() {
        // given
        val account = AccountRequest(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isDriver = true,
            carPlate = "ABC1234",
            password = "123456"
        )
        val accountResponse = Signup().execute(account)
        val ride = RideRequest(
            passengerId = accountResponse.accountId,
            fromLat = -6.3637562,
            fromLong = -36.970218,
            toLat = -6.4599549,
            toLong = -37.0937225,
        )
        // when
        val exception = assertFailsWith<Exception> {
            requestRide.execute(ride)
        }
        // then
        assertEquals("Not a Passenger", exception.message)
    }

    @Test
    fun givenRideWithPassengerWithRideInProgress_whenCallRequestRide_thenReturnPassengerWithRideInProgressException() {
        // given
        val account = AccountRequest(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isDriver = true,
            carPlate = "ABC1234",
            password = "123456"
        )
        val accountResponse = Signup().execute(account)
        val ride = RideRequest(
            passengerId = accountResponse.accountId,
            fromLat = -6.3637562,
            fromLong = -36.970218,
            toLat = -6.4599549,
            toLong = -37.0937225,
        )
        // when
        val exception = assertFailsWith<Exception> {
            requestRide.execute(ride)
        }
        // then
        assertEquals("Not a Passenger", exception.message)
    }
}