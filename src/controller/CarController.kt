package com.moobie.controller

import com.moobie.model.Car
import com.moobie.service.CarService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.routing.*
import javax.inject.Inject

class CarController @Inject constructor(application: Application, carService: CarService) {

    init {
        application.routing {
            route("car") {
                get("{id}") {
                    carService.get(call.parameters["id"]?.toInt())
                        ?.run {
                            call.respond(HttpStatusCode.OK, this)
                        } ?: call.respond(HttpStatusCode.BadRequest)
                }

                get("/list") {
                    call.respond(HttpStatusCode.OK, carService.list())
                }

                post {
                    carService.create(call.receiveOrNull<Car>())
                        ?.run {
                            call.respond(HttpStatusCode.Created, this)
                        } ?: call.respond(HttpStatusCode.BadRequest)
                }

                put {
                    carService.update(call.receiveOrNull<Car>())
                        ?.run {
                            call.respond(HttpStatusCode.OK, this)
                        } ?: call.respond(HttpStatusCode.BadRequest)
                }

                delete("{id}") {
                    carService.delete(call.parameters["id"]?.toInt())

                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }
}