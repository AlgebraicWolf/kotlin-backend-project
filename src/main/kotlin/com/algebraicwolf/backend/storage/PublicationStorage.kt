package com.algebraicwolf.backend.storage

import com.algebraicwolf.backend.model.Publication

interface PublicationStorage {
    fun getAll(): Collection<Publication>

    fun getById(id: Long): Result<Publication>

    fun publish(pubText: String): Result<Publication>

    fun delete(id: Long): Result<Unit>

    fun update(id: Long, newText: String): Result<Unit>
}