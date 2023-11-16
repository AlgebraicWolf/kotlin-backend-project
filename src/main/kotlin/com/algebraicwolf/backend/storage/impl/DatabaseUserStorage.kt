package com.algebraicwolf.backend.storage.impl

import com.algebraicwolf.backend.storage.UserStorage
import com.algebraicwolf.backend.storage.model.UserEntity
import com.algebraicwolf.backend.storage.model.UsersTable
import com.algebraicwolf.backend.utils.generateSalt
import com.algebraicwolf.backend.utils.hashPassword
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseUserStorage : UserStorage {
    override fun register(username: String, password: String, newName: String): Boolean {
        return transaction {
            val usernameFree = UserEntity.find{UsersTable.login eq username}.empty()

            if (usernameFree) {
                val newSalt = generateSalt()
                UserEntity.new {
                    login = username
                    name = newName
                    salt = newSalt
                    passwordHash = hashPassword(password, newSalt)
                }
            }

            usernameFree
        }
    }

    override fun login(username: String, password: String): Boolean {
        return transaction {
            val user = UserEntity.find{ UsersTable.login eq username }.firstOrNull()

            if (user != null) {
                hashPassword(password, user.salt) == user.passwordHash
            } else {
                false
            }
        }
    }
}