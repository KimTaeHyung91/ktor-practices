package com.example.plugins

import com.example.common.fail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*

fun Application.configureException() {
    install(StatusPages) {
        exception<IllegalArgumentException>() { call, cause ->
            call.fail(statusCode = HttpStatusCode.BadRequest, message = cause.message ?: "Internal Server Error")
        }

        exception<NotFoundException> { call, cause ->
            call.fail(statusCode = HttpStatusCode.NotFound, message = cause.message ?: "Internal Server Error")
        }
    }
}