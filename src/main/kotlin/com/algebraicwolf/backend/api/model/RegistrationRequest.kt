package com.algebraicwolf.backend.api.model

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest (
    val username: String,
    val password: String,
    val name: String
)