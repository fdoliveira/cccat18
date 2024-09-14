package com.example

import com.example.plugins.configureSerialization
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.postgresql.util.PGobject
import java.sql.DriverManager
import java.sql.PreparedStatement

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Serializable
data class Account(
    val accountId: String? = null,
    val name: String,
    val email: String,
    val cpf: String,
    @SerialName("is_passenger") val isPassenger: Boolean = false,
    @SerialName("is_driver") val isDriver: Boolean = false,
    @SerialName("car_plate") val carPlate: String? = null,
    val password: String
)

@Serializable
data class Response(@SerialName("account_id") val accountId: String)

fun idToPgObject(uuid: String): PGobject {
    val objectUuid = PGobject()
    objectUuid.setType("uuid")
    objectUuid.setValue(uuid)
    return objectUuid
}

fun setInsertStmtParams(stmt: PreparedStatement, id: PGobject, account: Account) {
    stmt.setObject(1, id)
    stmt.setString(2, account.name)
    stmt.setString(3, account.email)
    stmt.setString(4, account.cpf)
    stmt.setBoolean(5, account.isPassenger)
    stmt.setBoolean(6, account.isDriver)
    stmt.setString(7, account.carPlate)
    stmt.setString(8, account.password)
}

fun Application.module() {
    configureSerialization()

    routing {
        get("/") {
            call.respond(
                message = "Curso Clean Code e Clean Architect - Turma 18 - Branas.io",
                status = HttpStatusCode.OK
            )
        }

        post("/signup") {
            val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", "postgres", "123456")
            val input = call.receive<Account>()
            try {
                var response: Response? = null
                val id = java.util.UUID.randomUUID().toString()
                val statement = conn.prepareStatement("SELECT account_id FROM ccca.account WHERE email = ?")
                statement.setString(1, input.email)
                val accountData = statement.executeQuery()
                if (accountData.next()) {
                    call.respond(message = -4, status = HttpStatusCode.UnprocessableEntity)
                    return@post
                }
                if (!input.name.matches(Regex("^[a-zA-Z]+ [a-zA-Z]+$"))) {
                    call.respond(message = -3, status = HttpStatusCode.UnprocessableEntity)
                    return@post
                }
                if (!input.email.matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))) {
                    call.respond(message = -2, status = HttpStatusCode.UnprocessableEntity)
                    return@post
                }
                if (!validateCpf(input.cpf)) {
                    call.respond(message = -1, status = HttpStatusCode.UnprocessableEntity)
                    return@post
                }
                if (input.isDriver == true && (input.carPlate == null || (!input.carPlate.matches(Regex("^[A-Z]{3}[0-9]{4}$"))))) {
                    call.respond(message = -5, status = HttpStatusCode.UnprocessableEntity)
                    return@post
                }
                val stmt = conn.prepareStatement("INSERT INTO ccca.account (account_id, name, email, cpf, is_passenger, is_driver, car_plate, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
                setInsertStmtParams(stmt, idToPgObject(id), input)
                stmt.executeUpdate()
                response = Response(id)
                call.respond(
                    message = response,
                    status = HttpStatusCode.Created
                )
            } finally {
                conn.close()
            }
        }
        get("/accounts/{accountId}") {
            val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", "postgres", "123456")
            val accountId = call.parameters["accountId"]
            try {
                val statement = conn.prepareStatement("SELECT account_id, name, email, cpf, is_passenger, is_driver, car_plate FROM ccca.account WHERE account_id = ?")
                statement.setObject(1, idToPgObject(accountId!!))
                val resultSet = statement.executeQuery()
                if (resultSet.next()) {
                    val account = Account(
                        accountId = resultSet.getObject("account_id").toString(),
                        name = resultSet.getString("name"),
                        email = resultSet.getString("email"),
                        cpf = resultSet.getString("cpf"),
                        isPassenger = resultSet.getBoolean("is_passenger"),
                        isDriver = resultSet.getBoolean("is_driver"),
                        carPlate = resultSet.getString("car_plate"),
                        password = ""
                    )
                    call.respond(
                        message = account,
                        status = HttpStatusCode.OK
                    )
                } else {
                    call.respond(
                        message = "Account not found",
                        status = HttpStatusCode.NotFound
                    )
                }
            } finally {
                conn.close()
            }
        }
    }
}
