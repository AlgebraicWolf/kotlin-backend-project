package com.algebraicwolf.backend.model

import com.algebraicwolf.backend.InstantSerializer
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class UserData (
    // Since we are for now keeping everything in memory, and the data
    // accesses are limited, it's okay to store password in a plain
    // format without hashing.
    val password: String,
    val name: String,
    @Serializable(InstantSerializer::class)
    val createdAt: Instant
)