package com.example.domain

data class Email(val value: String) {
    init {
        if (!value.matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))) {
            throw Exception("Invalid email")
        }
    }
}