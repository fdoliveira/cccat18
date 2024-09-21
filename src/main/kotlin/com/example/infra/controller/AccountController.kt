package com.example.infra.controller

import com.example.app.usecase.account.GetAccount
import com.example.app.usecase.account.Signup
import com.example.infra.account.model.AccountRequest
import com.example.infra.account.model.GetAccountResponse
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
        val input = call.receive<AccountRequest>()
        val signup = Signup()
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
}