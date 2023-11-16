package com.algebraicwolf.backend

import com.algebraicwolf.backend.storage.UserStorage
import com.algebraicwolf.backend.storage.impl.DatabaseUserStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val userStorageModule = module {
    singleOf(::DatabaseUserStorage) bind UserStorage::class
}