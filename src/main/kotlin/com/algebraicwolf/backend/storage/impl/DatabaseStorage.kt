package com.algebraicwolf.backend.storage.impl

import com.algebraicwolf.backend.model.Publication
import com.algebraicwolf.backend.storage.PublicationStorage
import com.algebraicwolf.backend.storage.model.PublicationEntity
import com.algebraicwolf.backend.storage.model.PublicationsTable
import com.algebraicwolf.backend.storage.model.UserEntity
import com.algebraicwolf.backend.storage.model.UsersTable
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseStorage : PublicationStorage {
    companion object {
        private const val MAX_PUBS_COUNT = 1024
    }

    override fun getAll(): Collection<Publication> {
        return transaction {
            val query = PublicationsTable.selectAll().limit(MAX_PUBS_COUNT)
            PublicationEntity
                .wrapRows(query)
                .mapNotNull { it.toPublication() }
        }
    }

    override fun getPage(perPage: Int, page: Int): Collection<Publication> {
        return transaction {
            val query = PublicationsTable.selectAll().orderBy(
                PublicationsTable.createdAt,
                SortOrder.DESC
            ).limit(perPage, (page * perPage).toLong())
            PublicationEntity
                .wrapRows(query)
                .mapNotNull { it.toPublication() }
        }
    }

    override fun getById(id: Long): Publication? {
        return transaction { PublicationEntity.findById(id)?.toPublication() }
    }

    override fun publish(pubText: String, pubAuthor: String): Publication? {
        return transaction {
            val id = UserEntity.find{ UsersTable.login eq pubAuthor }.firstOrNull()?.id

            if (id != null) {
                PublicationEntity.new {
                    text = pubText
                    authorId = id.value
                }.toPublication()
            } else {
                null
            }
        }
    }

    override fun delete(id: Long): Boolean {
        return transaction {
            val obj = PublicationEntity.findById(id)
            if (obj != null) {
                obj.delete()
                true
            } else {
                false
            }
        }
    }

    override fun update(id: Long, newText: String): Boolean {
        return transaction {
            val obj = PublicationEntity.findById(id)
            if (obj != null) {
                obj.text = newText
                obj.modifiedAt = Clock.System.now()
                true
            } else {
                false
            }
        }
    }

    private fun PublicationEntity.toPublication(): Publication? {
        val user = UserEntity.findById(authorId)

        return if (user != null) {
            return Publication(id.value, text, createdAt, modifiedAt, user.login)
        } else {
            null
        }
    }
}