package com.example.infra.database

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import org.postgresql.util.PGobject
import java.sql.ResultSet

fun uuidToPgObject(uuid: String): PGobject {
    val objectUuid = PGobject()
    objectUuid.setType("uuid")
    objectUuid.setValue(uuid)
    return objectUuid
}

fun ResultSet.getKotlinxLocalDateTime(columnLabel: String): LocalDateTime? {
    val timestamp = this.getTimestamp(columnLabel)
    return timestamp?.toLocalDateTime()?.toKotlinLocalDateTime()
}

