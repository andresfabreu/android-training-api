package com.paringapi.service

import com.paringapi.database.StoreTable
import com.paringapi.database.insertIntoDatabase
import com.paringapi.database.updateIntoDatabase
import com.paringapi.model.Store
import com.paringapi.model.toStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class StoreServiceImpl : StoreService {
    override suspend fun list(): List<Store> = withContext(Dispatchers.IO) {
        transaction { StoreTable.selectAll().map { it.toStore() } }
    }

    override suspend fun create(newStore: Store): Store = withContext(Dispatchers.IO) {
        val id = transaction {
            newStore.run {
                this.insertIntoDatabase() get StoreTable.id
            }
        }

        return@withContext get(id)
    }

    override suspend fun update(updatedStore: Store): Store = withContext(Dispatchers.IO) {
        transaction {
            updatedStore.run {
                this.updateIntoDatabase()
            }
        }

        return@withContext get(updatedStore.id)
    }

    override suspend fun get(id: Int): Store = withContext(Dispatchers.IO) {
        transaction {
            StoreTable.select { StoreTable.id.eq(id) }.first().toStore()
        }
    }

    override suspend fun delete(id: Int) = withContext(Dispatchers.IO) {
        transaction {
            StoreTable.deleteWhere {
                StoreTable.id eq (id)
            }
        }

        return@withContext
    }
}