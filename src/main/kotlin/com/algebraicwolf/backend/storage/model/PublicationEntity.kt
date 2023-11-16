package com.algebraicwolf.backend.storage.model

import com.algebraicwolf.backend.storage.model.UsersTable.references
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class PublicationEntity(id: EntityID<Long>): LongEntity(id) {
    companion object: LongEntityClass<PublicationEntity>(PublicationsTable)
    var text by PublicationsTable.text
    var createdAt by PublicationsTable.createdAt
    var modifiedAt by PublicationsTable.modifiedAt
    var authorId by PublicationsTable.authorId.references(UsersTable.id)
}