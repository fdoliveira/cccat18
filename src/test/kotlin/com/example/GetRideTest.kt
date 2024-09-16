package com.example

import java.util.UUID
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetRideTest {
    private lateinit var rideDAO: RideDAO
    private lateinit var accountDAO: AccountDAO
    private lateinit var getRide: GetRide
    private lateinit var createRide: CreateRide

    @BeforeTest
    fun setup() {
        rideDAO = RideDAOMemory()
        accountDAO = AccountDAOMemory()
        getRide = GetRide(rideDAO)
        createRide = CreateRide(rideDAO, accountDAO)
    }

    @Test
    fun givenValidRideId_whenCallGetRide_thenReturnSavedRide() {
        // given
        val account = Account(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        val accountResponse = Signup(accountDAO, MailerGatewayMemory()).execute(account)
        val ride = Ride(
            passengerId = accountResponse.accountId,
            fromLat = -6.3637562,
            fromLong = -36.970218,
            toLat = -6.4599549,
            toLong = -37.0937225,
        )
        val response = createRide.execute(ride)
        val rideId = response.rideId
        // when
        val savedRide = getRide.execute(rideId)
        // then
        assertEquals(savedRide.rideId, rideId)
        assertEquals(savedRide.passengerId, ride.passengerId)
        assertEquals(savedRide.status, "requested")
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