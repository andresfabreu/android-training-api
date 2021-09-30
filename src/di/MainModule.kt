package com.paringapi.di

import com.paringapi.controller.StoreController
import com.paringapi.database.dbConnect
import com.paringapi.service.StoreService
import com.paringapi.service.StoreServiceImpl
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

        bind(StoreController::class.java).asEagerSingleton()
        bind(StoreService::class.java).to(StoreServiceImpl::class.java).asEagerSingleton()
        bind(Application::class.java).toInstance(application)
    }
}