package com.example

class MailerGatewayMemory: MailerGateway {
    override fun send(recipient: String, subject: String, message: String) {
        println("Sending email to $recipient with subject $subject and message $message")
    }
}