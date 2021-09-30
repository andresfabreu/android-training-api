package com.paringapi.database

import com.paringapi.model.Store
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.update

object StoreTable : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 255)
    val iconUrl = varchar("iconUrl", 255)
    val latitude = double("latitude")
    val longitude = double("longitude")
}

fun Store.insertIntoDatabase() : InsertStatement<Number> {
    return StoreTable.insert {
        with(this@insertIntoDatabase) {
            it[StoreTable.name] = name
            it[StoreTable.iconUrl] = iconUrl
            it[StoreTable.latitude] = latitude
            it[StoreTable.longitude] = longitude
        }
    }
}

fun Store.updateIntoDatabase() {
    StoreTable.update({ StoreTable.id eq this@updateIntoDatabase.id }) {
        with(this@updateIntoDatabase) {
            it[StoreTable.name] = name
            it[StoreTable.iconUrl] = iconUrl
            it[StoreTable.latitude] = latitude
            it[StoreTable.longitude] = longitude
        }
    }
}