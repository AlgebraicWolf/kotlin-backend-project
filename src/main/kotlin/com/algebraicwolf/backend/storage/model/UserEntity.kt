package com.algebraicwolf.backend.storage.model

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Long>): LongEntity(id) {
    companion object: LongEntityClass<UserEntity>(UsersTable)
    var login by UsersTable.login
    var name by UsersTable.name
    var createdAt by UsersTable.createdAt
    var salt by UsersTable.salt
    var passwordHash by UsersTable.passwordHash
}