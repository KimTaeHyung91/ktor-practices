package com.example.common

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

enum class Result {
    SUCCESS, FAIL
}

@Serializable
data class BaseResponse<T>(val message: String? = null, val result: Result = Result.SUCCESS, val data: T)


suspend fun <T> ApplicationCall.success(message: String? = null, data: T) =
    respond(BaseResponse(message = message, data = data))

suspend fun ApplicationCall.fail(message: String, statusCode: HttpStatusCode) =
    respond(status = statusCode, message = BaseResponse(message = message, result = Result.FAIL, data = null))