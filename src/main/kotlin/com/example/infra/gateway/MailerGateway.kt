package com.example.infra.gateway

interface MailerGateway {
    fun send(recipient: String, subject: String, message: String)
}