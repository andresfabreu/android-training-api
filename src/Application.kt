package com.paringapi

import com.paringapi.di.MainModule
import com.google.inject.Guice
import io.ktor.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    Guice.createInjector(MainModule(this))
}