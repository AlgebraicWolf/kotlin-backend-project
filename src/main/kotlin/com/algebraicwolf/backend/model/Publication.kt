package com.algebraicwolf.backend.model

import com.algebraicwolf.backend.InstantSerializer
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class Publication(
    val id: Long,
    val text: String,
    @Serializable(InstantSerializer::class)
    val createdAt: Instant,
    @Serializable(InstantSerializer::class)
    val modifiedAt: Instant,
    val author: String
)
