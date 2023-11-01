package com.algebraicwolf.backend.api.model

import kotlinx.serialization.Serializable

@Serializable
data class PublicationRequest(
    val pubText: String
)
