package com.algebraicwolf.backend.model

import java.time.Instant

data class Publication(
    val id: Long,
    val text: String,
    val createdAt: Instant,
    val modifiedAt: Instant
)
