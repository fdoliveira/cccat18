package com.example.infra.controller

import com.example.app.usecase.ride.GetRide
import com.example.app.usecase.ride.RequestRide
import com.example.infra.ride.model.GetRideResponse
import com.example.infra.ride.model.RequestRideRequest
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
        val rideCommand = getRide.execute(rideId)
        val rideResponse = GetRideResponse.from(rideCommand)
        call.respond(
            message = rideResponse,
            status = HttpStatusCode.OK
        )
    }
    post("/rides") {
        val input = call.receive<RequestRideRequest>()
        val requestRideCommand = input.toRequestRideCommand()
        val ride = RequestRide()
        try {
            val response = ride.execute(requestRideCommand)
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