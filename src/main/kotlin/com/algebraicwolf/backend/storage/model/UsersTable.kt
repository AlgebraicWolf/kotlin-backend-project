package com.algebraicwolf.backend.storage.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import java.security.SecureRandom

object UsersTable : LongIdTable() {
    val login: Column<String> = varchar("login", 64)
    val name: Column<String> = varchar("name", 64)
    val createdAt: Column<Instant> = timestamp("created_at").clientDefault { Clock.System.now() }
    val salt: Column<String> = varchar("salt", 64)
    val passwordHash: Column<String> = varchar("password_hash", 64)
}