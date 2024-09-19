package com.example.domain

import kotlinx.serialization.Serializable
import java.util.UUID.randomUUID

@Serializable
data class UUID(val value: String) {
    companion object {
        fun create(): UUID {
            val uuid = randomUUID().toString()
            return UUID(uuid)
        }
    }
}
