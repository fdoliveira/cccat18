package com.example.infra.repository

import com.example.domain.Account
import com.example.infra.database.DatabaseConnection
import com.example.infra.database.DatabasePgsqlAdapter
import com.example.infra.database.uuidToPgObject
import java.sql.PreparedStatement
import java.sql.Types

class AccountRepositoryDatabase: AccountRepository {
    private val connection: DatabaseConnection by lazy { DatabasePgsqlAdapter() }

    private fun setInsertStmtParams(stmt: PreparedStatement, account: Account): String {
        stmt.setObject(1, uuidToPgObject(account.getAccountId()!!))
        stmt.setString(2, account.getName())
        stmt.setString(3, account.getEmail())
        stmt.setString(4, account.getCPF())
        stmt.setBoolean(5, account.isPassenger())
        stmt.setBoolean(6, account.isDriver())
        if (account.getCarPlate() == null) stmt.setNull(7, Types.VARCHAR) else stmt.setString(7, account.getCarPlate())
        stmt.setString(8, account.getPassword())
        return account.getAccountId()!!
    }

    override fun getAccountById(accountId: String): Account? {
        val conn = connection.getConnection()
        val statement = conn.prepareStatement("SELECT account_id, name, email, cpf, is_passenger, is_driver, car_plate FROM ccca.account WHERE account_id = ?")
        statement.setObject(1, uuidToPgObject(accountId))
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
    }

    override fun getAccountByEmail(email: String): Account? {
        val conn = connection.getConnection()
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
    }

    override fun saveAccount(account: Account): String {
        val conn = connection.getConnection()
        val stmt =
            conn.prepareStatement("INSERT INTO ccca.account (account_id, name, email, cpf, is_passenger, is_driver, car_plate, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
        val id = setInsertStmtParams(stmt, account)
        stmt.executeUpdate()
        return id
    }
}