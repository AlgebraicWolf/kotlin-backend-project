package com.algebraicwolf.backend.storage

import com.algebraicwolf.backend.model.Publication

interface PublicationStorage {
    fun getAll(): Collection<Publication>

    fun getPage(perPage: Int, page: Int): Collection<Publication>

    fun getById(id: Long): Publication?

    fun publish(pubText: String): Publication?

    fun delete(id: Long): Boolean

    fun update(id: Long, newText: String): Boolean
}