package com.algebraicwolf.backend.api.model

import com.algebraicwolf.backend.model.Publication
import kotlinx.serialization.Serializable

@Serializable
data class PublicationsResp(
    val pubs: Array<Publication>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PublicationsResp

        return pubs.contentEquals(other.pubs)
    }

    override fun hashCode(): Int {
        return pubs.contentHashCode()
    }
}
