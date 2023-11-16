package com.algebraicwolf.backend.storage.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp


object PublicationsTable : LongIdTable() {
    val text: Column<String> = varchar("text", length = 500)
    val createdAt: Column<Instant> = timestamp("created_at")
        .clientDefault { Clock.System.now() }
    val modifiedAt: Column<Instant> = timestamp("modified_at")
        .clientDefault { Clock.System.now() }
    val authorId: Column<Long> = long("author_id")
}