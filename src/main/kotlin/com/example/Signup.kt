package com.example

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.postgresql.util.PGobject
import java.sql.DriverManager
import java.sql.PreparedStatement

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

fun getAccountById(accountId: String): Account? {
    val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", "postgres", "123456")
    try {
        val statement = conn.prepareStatement("SELECT account_id, name, email, cpf, is_passenger, is_driver, car_plate FROM ccca.account WHERE account_id = ?")
        statement.setObject(1, idToPgObject(accountId))
        val resultSet = statement.executeQuery()
        if (resultSet.next()) {
            return Account(
                accountId = resultSet.getObject("account_id").toString(),
                name = resultSet.getString("name"),
                email = resultSet.getString("email"),
                cpf = resultSet.getString("cpf"),
                isPassenger = resultSet.getBoolean("is_passenger"),
                isDriver = resultSet.getBoolean("is_driver"),
                carPlate = resultSet.getString("car_plate"),
                password = ""
            )
        }
        return null
    } finally {
        conn.close()
    }
}

fun getAccountByEmail(email: String): Account? {
    val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", "postgres", "123456")
    try {
        val statement = conn.prepareStatement("SELECT account_id, name, email, cpf, is_passenger, is_driver, car_plate FROM ccca.account WHERE email = ?")
        statement.setString(1, email)
        val resultSet = statement.executeQuery()
        if (resultSet.next()) {
            return Account(
                accountId = resultSet.getObject("account_id").toString(),
                name = resultSet.getString("name"),
                email = resultSet.getString("email"),
                cpf = resultSet.getString("cpf"),
                isPassenger = resultSet.getBoolean("is_passenger"),
                isDriver = resultSet.getBoolean("is_driver"),
                carPlate = resultSet.getString("car_plate"),
                password = ""
            )
        }
        return null
    } finally {
        conn.close()
    }
}

fun saveAccount(account: Account): String {
    val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", "postgres", "123456")
    try {
        val id = java.util.UUID.randomUUID().toString()
        val stmt = conn.prepareStatement("INSERT INTO ccca.account (account_id, name, email, cpf, is_passenger, is_driver, car_plate, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
        setInsertStmtParams(stmt, idToPgObject(id), account)
        stmt.executeUpdate()
        return id
    } finally {
        conn.close()
    }
}

fun signup(input: Account): Response {
    getAccountByEmail(input.email)?.let { throw Exception("Duplicated account") }
    (!input.name.matches(Regex("^[a-zA-Z]+ [a-zA-Z]+$"))) && throw Exception("Invalid name")
    (!input.email.matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))) && throw Exception("Invalid email")
    (!validateCpf(input.cpf)) && throw Exception("Invalid cpf")
    (input.isDriver && (input.carPlate == null || (!input.carPlate.matches(Regex("^[A-Z]{3}[0-9]{4}$"))))) && throw Exception("Invalid car plate")
    return Response(saveAccount(input))
}

fun getAccount(accountId: String): Account {
    return getAccountById(accountId) ?: throw Exception("Account not found")
}