package com.example.infra.di

import com.example.infra.gateway.MailerGateway
import com.example.infra.gateway.MailerGatewayMemory
import org.koin.dsl.module

val mailerModule = module {
    single<MailerGateway> {
        MailerGatewayMemory()
    }
}