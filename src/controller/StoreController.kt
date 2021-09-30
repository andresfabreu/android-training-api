package com.paringapi.controller

import com.paringapi.service.StoreService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.*
import io.ktor.response.respond
import io.ktor.routing.*
import javax.inject.Inject

class StoreController @Inject constructor(application: Application, storeService: StoreService) {

    init {
        application.routing {
            route("store") {
                get("{id}") {
                    val store = storeService.get(checkNotNull(call.parameters["id"]?.toInt()))
                    call.respond(HttpStatusCode.OK, store)
                }

                get("/list") {
                    call.respond(HttpStatusCode.OK, storeService.list())
                }

                post {
                    val newStore = storeService.create(call.receive())
                    call.respond(HttpStatusCode.Created, newStore)
                }

                put {
                    val updatedStore = storeService.update(call.receive())
                    call.respond(HttpStatusCode.OK, updatedStore)
                }

                delete("{id}") {
                    storeService.delete(checkNotNull(call.parameters["id"]?.toInt()))
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }
}