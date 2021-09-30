package com.paringapi.model

import com.paringapi.database.StoreTable
import org.jetbrains.exposed.sql.ResultRow

data class Store(
    val id: Int,
    val name: String,
    val iconUrl: String,
    val latitude: Double,
    val longitude: Double
)

fun ResultRow.toStore(): Store {
    return Store(
        id = this[StoreTable.id],
        name = this[StoreTable.name],
        iconUrl = this[StoreTable.iconUrl],
        latitude = this[StoreTable.latitude],
        longitude = this[StoreTable.longitude]
    )
}