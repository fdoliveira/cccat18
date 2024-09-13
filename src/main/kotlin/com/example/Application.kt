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

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Serializable
data class Account(
    val name: String,
    val email: String,
    val cpf: String,
    @SerialName("is_driver") val isDriver: Boolean = false,
    @SerialName("car_plate") val carPlate: String?,
    val password: String
)

@Serializable
data class Response(val accountId: String)

fun idToPgObject(uuid: String): PGobject {
    val objectUuid = PGobject()
    objectUuid.setType("uuid")
    objectUuid.setValue(uuid)
    return objectUuid
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
                var result = 0
                val id = java.util.UUID.randomUUID().toString()
                val statement = conn.prepareStatement("SELECT account_id FROM ccca.account WHERE account_id = ?")
                statement.setObject(1, idToPgObject(id))
                val resultSet = statement.executeQuery()
                if (!resultSet.next()) {
                    if (input.name.matches(Regex("^[a-zA-Z]+ [a-zA-Z]+$"))) {
                        if (input.email.matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))) {
                            if (validateCpf(input.cpf)) {
                                if (input.isDriver && input.carPlate != null) {
                                    if (input.carPlate.matches(Regex("^[A-Z]{3}[0-9]{4}$"))) {
                                        val stmt =
                                            conn.prepareStatement("INSERT INTO ccca.account (account_id, name, email, cpf, is_driver, car_plate, password) VALUES (?, ?, ?, ?, ?, ?, ?)")
                                        stmt.setObject(1, idToPgObject(id))
                                        stmt.setString(2, input.name)
                                        stmt.setString(3, input.email)
                                        stmt.setString(4, input.cpf)
                                        stmt.setBoolean(5, input.isDriver)
                                        stmt.setString(6, input.carPlate)
                                        stmt.setString(7, input.password)
                                        stmt.executeUpdate()
                                        response = Response(id)
                                    } else {
                                        // invalid car plate
                                        result = -5
                                    }
                                } else {
                                    val stmt =
                                        conn.prepareStatement("INSERT INTO ccca.account (account_id, name, email, cpf, is_driver, car_plate, password) VALUES (?, ?, ?, ?, ?, ?, ?)")
                                    stmt.setObject(1, idToPgObject(id))
                                    stmt.setString(2, input.name)
                                    stmt.setString(3, input.email)
                                    stmt.setString(4, input.cpf)
                                    stmt.setBoolean(5, false)
                                    stmt.setString(6, null)
                                    stmt.setString(7, input.password)
                                    stmt.executeUpdate()
                                    response = Response(id)
                                }
                            } else {
                                // invalid cpf
                                result = -1
                            }
                        } else {
                            // invalid email
                            result = -2
                        }
                    } else {
                        // invalid name
                        result = -3
                    }
                } else {
                    // already exists
                    result = -4
                }
                if (response != null) {
                    call.respond(response)
                } else {
                    call.respond(
                        message = result,
                        status = HttpStatusCode.UnprocessableEntity
                    )
                }
            } finally {
                conn.close()
            }
        }
    }
}
