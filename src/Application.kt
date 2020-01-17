package com.moobie

import com.moobie.di.MainModule
import com.google.inject.Guice
import io.ktor.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    Guice.createInjector(MainModule(this))
}