package com.moobie.service

import com.moobie.model.Car

interface CarService {
    suspend fun list(): List<Car?>
    suspend fun create(newCar: Car?): Car?
    suspend fun update(updatedCar: Car?): Car?
    suspend fun get(id: Int?): Car?
    suspend fun delete(id: Int?)
}