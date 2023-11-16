package com.algebraicwolf.backend.model

import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant

@Serializable
data class Publication(
    val id: Long,
    val text: String,
    val createdAt: Instant,
    val modifiedAt: Instant,
    val author: String
)
