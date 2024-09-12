package com.example.plugins

import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(
            contentType = ContentType.Application.Json,
            json = kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            }
        )
    }
}
