package com.algebraicwolf.backend.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResp(
    val errorMsg: String
)
