package com.example.domain

data class Name(val value: String) {
    init {
        if (!value.matches(Regex("^[a-zA-Z]+ [a-zA-Z]+$"))) {
            throw Exception("Invalid name")
        }
    }
}