package com.example.infra.controller

import com.example.app.usecase.CreateRide
import com.example.app.usecase.GetRide
import com.example.infra.ride.model.GetRideResponse
import com.example.infra.ride.model.RideRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Routing.rideController() {
    get("/rides/{rideId}") {
        val rideId = call.parameters["rideId"]
        val getRide = GetRide()
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
        val ride = CreateRide()
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