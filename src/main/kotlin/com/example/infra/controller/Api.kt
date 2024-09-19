package com.example.infra.controller

import com.example.app.usecase.CreateRide
import com.example.app.usecase.GetAccount
import com.example.app.usecase.GetRide
import com.example.app.usecase.Signup
import com.example.infra.account.model.AccountRequest
import com.example.infra.account.model.GetAccountResponse
import com.example.infra.gateway.MailerGatewayMemory
import com.example.infra.repository.AccountDAOPgsql
import com.example.infra.repository.RideDAOPgsql
import com.example.infra.ride.model.GetRideResponse
import com.example.infra.ride.model.RideRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(
                message = "Curso Clean Code e Clean Architect - Turma 18 - Branas.io",
                status = HttpStatusCode.OK
            )
        }
        get("/accounts/{accountId}") {
            val accountId = call.parameters["accountId"]
            val getAccount = GetAccount(AccountDAOPgsql())
            if (accountId == null) {
                call.respond(
                    message = "Account not found",
                    status = HttpStatusCode.NotFound
                )
                return@get
            }
            val account = getAccount.execute(accountId)
            val accountResponse = GetAccountResponse.from(account)
            call.respond(
                message = accountResponse,
                status = HttpStatusCode.OK
            )
        }
        post("/signup") {
            val input = call.receive<AccountRequest>()
            val signup = Signup(AccountDAOPgsql(), MailerGatewayMemory())
            try {
                val response = signup.execute(input)
                call.respond(
                    message = response,
                    status = HttpStatusCode.Created
                )
            } catch (e: Exception) {
                call.respond(
                    message = e.message as String,
                    status = HttpStatusCode.UnprocessableEntity
                )
            }

        }
        get("/rides/{rideId}") {
            val rideId = call.parameters["rideId"]
            val getRide = GetRide(RideDAOPgsql())
            if (rideId == null) {
                call.respond(
                    message = "Ride not found",
                    status = HttpStatusCode.NotFound
                )
                return@get
            }
            val ride = getRide.execute(rideId)
            val rideResponse = GetRideResponse.from(ride)
            call.respond(
                message = rideResponse,
                status = HttpStatusCode.OK
            )
        }
        post("/rides") {
            val input = call.receive<RideRequest>()
            val ride = CreateRide(RideDAOPgsql(), AccountDAOPgsql())
            try {
                val response = ride.execute(input)
                call.respond(
                    message = response,
                    status = HttpStatusCode.Created
                )
            } catch (e: Exception) {
                call.respond(
                    message = e.message as String,
                    status = HttpStatusCode.UnprocessableEntity
                )
            }
        }
    }
}