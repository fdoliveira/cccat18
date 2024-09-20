package com.example.infra.database

import com.example.infra.plugins.DatabaseConfig
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.yaml.YamlConfig
import java.sql.Connection

class DatabasePgsqlAdapter : DatabaseConnection {
    private val conn: Connection

    init {
        val db = setupDatabaseConfiguration()
        conn = provideDataSource(db).connection
    }

    private fun setupDatabaseConfiguration(): DatabaseConfig {
        val config = YamlConfig("application.yaml")

        // Database
        val pgsqlDriver = config!!.property("ktor.database.driverClassName").getString()
        val pgsqlJdbcUrl = config.property("ktor.database.jdbcURL").getString()
        val pgsqlHost = config.property("ktor.database.host").getString()
        val pgsqlPort = config.property("ktor.database.port").getString()
        val pgsqlDatabaseName = config.property("ktor.database.databaseName").getString()
        val pgsqlUsername = config.property("ktor.database.username").getString()
        val pgsqlPassword = config.property("ktor.database.password").getString()
        val maxPoolSize = config.property("ktor.hikari.maxPoolSize").getString()
        val autoCommit = config.property("ktor.hikari.autoCommit").getString()

        val url = "$pgsqlJdbcUrl://$pgsqlHost:$pgsqlPort/$pgsqlDatabaseName"

        val databaseConfig = DatabaseConfig(
            pgsqlDriver,
            url,
            pgsqlUsername,
            pgsqlPassword,
            maxPoolSize.toInt(),
            autoCommit == "true"
        )

        return databaseConfig
    }

    private fun provideDataSource(dbConfig: DatabaseConfig): HikariDataSource {
        val hikariConfig = HikariConfig().apply {
            driverClassName = dbConfig.driverClass
            jdbcUrl = dbConfig.url
            username = dbConfig.user
            password = dbConfig.password
            maximumPoolSize = dbConfig.maxPoolSize
            isAutoCommit = dbConfig.autoCommit
            validate()
        }

        return HikariDataSource(hikariConfig)
    }

    override fun getConnection(): Connection {
        return conn
    }

    override fun close() {
        // not use in production
    }
}