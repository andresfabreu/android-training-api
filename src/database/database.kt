package com.moobie.database

import com.moobie.model.Car
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun dbConnect() {
    Database.connect(getConnection())
    transaction { SchemaUtils.create(CarTable) }

    mutableListOf(
        Car(
            1,
            "FIESTA 1.6 TI-VCT FLEX SE MANUAL 2018",
            "FORD",
            -23.562356,
            -46.6694725
        ),
        Car(
            2,
            "GOL 1.6 MSI TOTALFLEX TRENDLINE 4P MANUAL",
            "VOLKSWAGEN",
            -23.542096677319027,
            -46.658216017167874
        )
    ).forEach { transaction { it.insertIntoDatabase() } }
}

private fun getConnection(): HikariDataSource {
    val config = HikariConfig()
    config.driverClassName = "org.h2.Driver"
    config.jdbcUrl = "jdbc:h2:mem:test"
    config.maximumPoolSize = 3
    config.isAutoCommit = false
    config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    config.validate()

    return HikariDataSource(config)
}