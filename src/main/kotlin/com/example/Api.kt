package com.example

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
        post("/signup") {
            val input = call.receive<Account>()
            try {
                val response = signup(input)
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
        get("/accounts/{accountId}") {
            val accountId = call.parameters["accountId"]
            if (accountId == null) {
                call.respond(
                    message = "Account not found",
                    status = HttpStatusCode.NotFound
                )
                return@get
            }
            val account = getAccount(accountId)
            call.respond(
                message = account,
                status = HttpStatusCode.OK
            )
        }

    }
}