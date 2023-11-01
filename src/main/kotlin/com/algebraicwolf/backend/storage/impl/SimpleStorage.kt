package com.algebraicwolf.backend.storage.impl

import com.algebraicwolf.backend.model.Publication
import com.algebraicwolf.backend.storage.PublicationStorage
import java.time.Instant

class SimpleStorage : PublicationStorage {
    private val publications: MutableMap<Long, Publication> = mutableMapOf()
    private var nextId: Long = 0

    override fun getAll(): Collection<Publication> {
        return publications.values.sortedByDescending { it.createdAt }
    }

    override fun getPage(perPage: Int, page: Int): Collection<Publication> {
        // We consider pages to be zero-indexed
        return getAll().drop(perPage * page).take(perPage)
    }

    override fun getById(id: Long): Publication? {
        return publications[id]
    }

    override fun delete(id: Long): Boolean {
        return publications.remove(id) != null
    }

    override fun publish(pubText: String): Publication? {
        if (pubText.length > 500) {
            return null
        }

        val time = Instant.now()
        val newPub = Publication(nextId, pubText, time, time)
        publications[nextId] = newPub
        nextId += 1

        return newPub
    }

    override fun update(id: Long, newText: String): Boolean {
        val pub = publications[id]

        if (pub != null) {
            val newPub = pub.copy(text = newText, modifiedAt = Instant.now())
            publications[id] = newPub

            return true
        } else {
            return false
        }
    }
}