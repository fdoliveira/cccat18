package com.example

import com.example.app.usecase.ride.RequestRide
import com.example.app.usecase.ride.GetRide
import com.example.app.usecase.account.Signup
import com.example.infra.account.model.AccountRequest
import com.example.infra.di.repositoryModule
import com.example.infra.ride.model.RideRequest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import java.util.UUID
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetRideTest: KoinTest {
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
    fun givenValidRideId_whenCallGetRide_thenReturnSavedRide() {
        // given
        val account = AccountRequest(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
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
        val response = requestRide.execute(ride)
        val rideId = response.rideId
        // when
        val rideOutput = getRide.execute(rideId)
        // then
        assertEquals(rideOutput.rideId, rideId)
        assertEquals(rideOutput.passenger.accountId, ride.passengerId)
        assertEquals(rideOutput.status, "requested")
    }

    @Test
    fun givenInvalidRideId_whenCallGetRide_thenThrowException() {
        // given
        val invalidRideId = UUID.randomUUID().toString()
        // when
        val exception = assertFailsWith<Exception> {
            getRide.execute(invalidRideId)
        }
        // then
        assertEquals(exception.message, "Ride not found")
    }
}