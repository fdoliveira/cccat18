package com.example

interface MailerGateway {
    fun send(recipient: String, subject: String, message: String)
}