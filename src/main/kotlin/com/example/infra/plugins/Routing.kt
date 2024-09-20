package com.example.infra.plugins

import com.example.infra.controller.accountController
import com.example.infra.controller.rideController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(
                message = "Curso Clean Code e Clean Architect - Turma 18 - Branas.io",
                status = HttpStatusCode.OK
            )
        }

        accountController()
        rideController()
    }
}
