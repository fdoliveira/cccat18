package com.example.domain

import java.util.UUID.randomUUID

data class UUID(val value: String) {
    companion object {
        fun create(): UUID {
            val uuid = randomUUID().toString()
            return UUID(uuid)
        }
    }
}