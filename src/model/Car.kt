package com.moobie.model

import com.moobie.database.CarTable
import org.jetbrains.exposed.sql.ResultRow

data class Car(
    val id: Int,
    val model: String,
    val brand: String,
    val latitude: Double,
    val longitude: Double
)

fun ResultRow.toCar(): Car {
    return Car(
        id = this[CarTable.id],
        model = this[CarTable.model],
        brand = this[CarTable.brand],
        latitude = this[CarTable.latitude],
        longitude = this[CarTable.longitude]
    )
}