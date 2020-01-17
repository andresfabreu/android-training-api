package com.moobie.database

import com.moobie.model.Car
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.update

object CarTable : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val model = varchar("model", 255)
    val brand = varchar("brand", 255)
    val latitude = double("latitude")
    val longitude = double("longitude")
}

fun Car.insertIntoDatabase() : InsertStatement<Number> {
    return CarTable.insert {
        with(this@insertIntoDatabase) {
            it[CarTable.model] = model
            it[CarTable.brand] = brand
            it[CarTable.latitude] = latitude
            it[CarTable.longitude] = longitude
        }
    }
}

fun Car.updateIntoDatabase() {
    CarTable.update({ CarTable.id eq this@updateIntoDatabase.id }) {
        with(this@updateIntoDatabase) {
            it[CarTable.model] = model
            it[CarTable.brand] = brand
            it[CarTable.latitude] = latitude
            it[CarTable.longitude] = longitude
        }
    }
}