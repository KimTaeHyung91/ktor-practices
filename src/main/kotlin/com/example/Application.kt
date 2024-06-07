package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    startKoin {
        modules(DiModule().module)
    }
    configureMonitoring()
    configureSerialization()
    configureDatabases()
    configureRouting()
    configureException()
}