package com.algebraicwolf.backend.extensions

import com.algebraicwolf.backend.storage.model.PublicationsTable
import com.algebraicwolf.backend.storage.model.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Database.initSchema() {
    transaction(this) {
        SchemaUtils.create(UsersTable, PublicationsTable)
    }
}