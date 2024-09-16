package com.example

import org.postgresql.util.PGobject

fun idToPgObject(uuid: String): PGobject {
    val objectUuid = PGobject()
    objectUuid.setType("uuid")
    objectUuid.setValue(uuid)
    return objectUuid
}