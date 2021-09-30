package com.paringapi.service

import com.paringapi.model.Store

interface StoreService {
    suspend fun list(): List<Store>
    suspend fun create(newStore: Store): Store
    suspend fun update(updatedStore: Store): Store
    suspend fun get(id: Int): Store
    suspend fun delete(id: Int)
}