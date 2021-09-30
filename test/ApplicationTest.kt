package com.paringapi

import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/store/list").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}
