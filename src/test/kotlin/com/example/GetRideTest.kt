package com.example

import com.example.app.usecase.CreateRide
import com.example.app.usecase.GetRide
import com.example.app.usecase.Signup
import com.example.infra.account.model.AccountRequest
import com.example.infra.gateway.MailerGatewayMemory
import com.example.infra.repository.AccountDAO
import com.example.infra.repository.AccountDAOPgsql
import com.example.infra.repository.RideDAO
import com.example.infra.repository.RideDAOPgsql
import com.example.infra.ride.model.RideRequest
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
        rideDAO = RideDAOPgsql()
        accountDAO = AccountDAOPgsql()
        getRide = GetRide(rideDAO)
        createRide = CreateRide(rideDAO, accountDAO)
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
        val accountResponse = Signup(accountDAO, MailerGatewayMemory()).execute(account)
        val ride = RideRequest(
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
        assertEquals(savedRide.getRideId(), rideId)
        assertEquals(savedRide.getPassengerId(), ride.passengerId)
        assertEquals(savedRide.getStatus(), "requested")
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