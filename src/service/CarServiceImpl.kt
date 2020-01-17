package com.moobie.service

import com.moobie.database.CarTable
import com.moobie.database.insertIntoDatabase
import com.moobie.database.updateIntoDatabase
import com.moobie.model.Car
import com.moobie.model.toCar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class CarServiceImpl : CarService {
    override suspend fun list(): List<Car?> = withContext(Dispatchers.IO) {
        transaction { CarTable.selectAll().map { it.toCar() } }
    }

    override suspend fun create(newCar: Car?): Car? = withContext(Dispatchers.IO) {
        val id = transaction {
            newCar?.run {
                this.insertIntoDatabase() get CarTable.id
            }
        }

        return@withContext get(id)
    }

    override suspend fun update(updatedCar: Car?): Car? = withContext(Dispatchers.IO) {
        transaction {
            updatedCar?.run {
                this.updateIntoDatabase()
            }
        }

        return@withContext get(updatedCar?.id)
    }

    override suspend fun get(id: Int?): Car? = withContext(Dispatchers.IO) {
        transaction {
            CarTable.select { CarTable.id.eq(id ?: -1) }.firstOrNull()?.toCar()
        }
    }

    override suspend fun delete(id: Int?) = withContext(Dispatchers.IO) {
        transaction {
            CarTable.deleteWhere {
                CarTable.id eq (id ?: -1)
            }
        }

        return@withContext
    }
}