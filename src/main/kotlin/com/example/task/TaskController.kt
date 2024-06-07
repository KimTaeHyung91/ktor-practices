package com.example.task

import com.example.common.success
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Application.taskRouting() {
    val taskUseCase by inject<TaskUseCase>()

    routing {
        route("/api/v1/tasks") {
            get {
                val taskDtos = taskUseCase.getTask()

                call.success(data = taskDtos)
            }

            get("{id}") {
                val id = taskUseCase.getTaskBy(call.parameters["id"]!!.toInt())
                call.success(data = id)
            }

            post {
                val saveTaskCommand = call.receive<TaskUseCase.SaveTaskCommand>()
                val id = taskUseCase.saveTask(saveTaskCommand)
                call.success(data = id)
            }

            put("{id}") {
                val modifyTaskCommand = call.receive<TaskUseCase.ModifyTaskCommand>()
                taskUseCase.modifyTask(call.parameters["id"]!!.toInt(), modifyTaskCommand)
                call.success(data = null)
            }

            delete("{id}") {
                taskUseCase.removeTask(call.parameters["id"]!!.toInt())
                call.success(data = null)
            }
        }
    }
}