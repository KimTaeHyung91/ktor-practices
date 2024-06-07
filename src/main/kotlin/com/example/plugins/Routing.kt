package com.example.plugins

import com.example.task.taskRouting
import io.ktor.server.application.*


fun Application.configureRouting() {
    taskRouting()
}
