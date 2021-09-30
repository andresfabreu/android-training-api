package com.paringapi.database

import com.paringapi.model.Store
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun dbConnect() {
    Database.connect(getConnection())
    transaction { SchemaUtils.create(StoreTable) }

    mutableListOf(
        Store(
            1,
            "Magazine Luiza",
            "https://imgsapp.impresso.correioweb.com.br/app/da_impresso_130686904244/2020/03/30/325011/20200329211503546282i.jpg",
            -23.562356,
            -46.6694725
        ),
        Store(
            2,
            "Lojas Americanas",
            "https://s2.glbimg.com/Dgf_qkF--yAtoBEDeaPPVWmLvOg=/0x20:650x397/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_63b422c2caee4269b8b34177e8876b93/internal_photos/bs/2019/A/g/cQ3xPVTaee8zHQ4549fw/lojasamericanas.jpg",
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