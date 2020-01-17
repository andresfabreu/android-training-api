package com.moobie.di

import com.moobie.controller.CarController
import com.moobie.database.dbConnect
import com.moobie.service.CarService
import com.moobie.service.CarServiceImpl
import com.google.inject.AbstractModule
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson

class MainModule(private val application: Application) : AbstractModule() {
    override fun configure() {
        application.install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }

        application.install(DefaultHeaders)

        dbConnect()

        bind(CarController::class.java).asEagerSingleton()
        bind(CarService::class.java).to(CarServiceImpl::class.java).asEagerSingleton()
        bind(Application::class.java).toInstance(application)
    }
}