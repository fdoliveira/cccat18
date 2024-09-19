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
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateRideTest {
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
    fun givenValidRide_whenCallCreateRide_thenCreateRide() {
        // given
        val accountRequest = AccountRequest(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        val accountResponse = Signup(accountDAO, MailerGatewayMemory()).execute(accountRequest)
        val rideRequest = RideRequest(
            passengerId = accountResponse.accountId,
            fromLat = -6.3637562,
            fromLong = -36.970218,
            toLat = -6.4599549,
            toLong = -37.0937225,
        )
        val response = createRide.execute(rideRequest)
        // then
        assert(response.rideId.isNotEmpty())
    }

    @Test
    fun givenRideWithDriver_whenCallCreateRide_thenReturnNotPassengerException() {
        // given
        val account = AccountRequest(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isDriver = true,
            carPlate = "ABC1234",
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
        // when
        val exception = assertFailsWith<Exception> {
            createRide.execute(ride)
        }
        // then
        assertEquals("Not a Passenger", exception.message)
    }

    @Test
    fun givenRideWithPassengerWithRideInProgress_whenCallCreateRide_thenReturnPassengerWithRideInProgressException() {
        // given
        val account = AccountRequest(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isDriver = true,
            carPlate = "ABC1234",
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
        // when
        val exception = assertFailsWith<Exception> {
            createRide.execute(ride)
        }
        // then
        assertEquals("Not a Passenger", exception.message)
    }
}