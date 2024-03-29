package com.algebraicwolf.backend.storage.impl

import com.algebraicwolf.backend.model.Publication
import com.algebraicwolf.backend.storage.PublicationStorage
import kotlinx.datetime.Clock
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

    override fun publish(pubText: String, author: String): Publication? {
        if (pubText.length > 500) {
            return null
        }

        val time = Clock.System.now()
        val newPub = Publication(nextId, pubText, time, time, author)
        publications[nextId] = newPub
        nextId += 1

        return newPub
    }

    override fun update(id: Long, newText: String): Boolean {
        val pub = publications[id]

        return if (pub != null) {
            publications[id] = pub.copy(text = newText, modifiedAt = Clock.System.now())
            true
        } else {
            false
        }
    }
}