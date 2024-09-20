package com.example.infra.plugins

import com.example.infra.di.mailerModule
import com.example.infra.di.repositoryModule
import io.ktor.server.application.*
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger

fun Application.configureKoin() {
    install(Koin){
        SLF4JLogger()
        modules(repositoryModule)
        modules(mailerModule)
    }
}