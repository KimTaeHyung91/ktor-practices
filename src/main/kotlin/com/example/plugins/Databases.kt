package com.example.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.StdOutSqlLogger

fun Application.configureDatabases() {
    Database.connect(
        datasource = HikariDataSource(
            HikariConfig().apply {
                jdbcUrl = "jdbc:postgresql://localhost:15432/my_db"
                driverClassName = "org.postgresql.Driver"
                username = "my_postgresql"
                password = "my_postgresql"
                maximumPoolSize = 6
                isReadOnly = false
            }
        ),
        databaseConfig = DatabaseConfig {
            sqlLogger = StdOutSqlLogger
        }
    )
}
