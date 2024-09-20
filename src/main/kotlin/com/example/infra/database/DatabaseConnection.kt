package com.example.infra.database

import java.sql.Connection

interface DatabaseConnection {
    fun getConnection(): Connection
    fun close()
}