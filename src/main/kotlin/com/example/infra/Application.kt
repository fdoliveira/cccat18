package com.example.infra

import com.example.infra.plugins.configureDatabase
import com.example.infra.plugins.configureKoin
import com.example.infra.plugins.configureRouting
import com.example.infra.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    configureKoin()
    configureDatabase()
    configureSerialization()
    configureRouting()
}

