package com.example.infra.controller

import com.example.app.usecase.account.GetAccount
import com.example.app.usecase.account.Signup
import com.example.infra.account.model.SignupRequest
import com.example.infra.account.model.GetAccountResponse
import com.example.infra.account.model.SignupResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Routing.accountController() {
    get("/accounts/{accountId}") {
        val accountId = call.parameters["accountId"]
        val getAccount = GetAccount()
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
        val input = call.receive<SignupRequest>()
        var signupCommand = input.toSignupCommand()
        val signup = Signup()
        try {
            val responseOutput = signup.execute(signupCommand)
            val signupResponse = SignupResponse.from(responseOutput)
            call.respond(
                message = signupResponse,
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