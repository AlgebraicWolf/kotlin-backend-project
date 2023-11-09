package com.algebraicwolf.backend.storage.impl

import com.algebraicwolf.backend.model.UserData
import com.algebraicwolf.backend.storage.UserStorage
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

class SimpleUserStorage: UserStorage {
    // This is all in our memory space and will never be stored outside,
    // hence hashing password is not required
    private val storage : ConcurrentHashMap<String, UserData> = ConcurrentHashMap()

    override fun login(username: String, password: String): Boolean {
        return storage[username]?.password == password
    }

    override fun register(username: String, password: String, name: String): Boolean {
        return storage.putIfAbsent(username, UserData(password, name, Instant.now())) == null
    }
}