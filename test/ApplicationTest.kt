package com.moobie

import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/car/list").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}
