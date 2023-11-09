package com.algebraicwolf.backend.storage

interface UserStorage {
    fun register(username: String, password: String, name: String): Boolean

    fun login(username: String, password: String): Boolean
}