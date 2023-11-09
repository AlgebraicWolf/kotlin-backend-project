package com.algebraicwolf.backend

import com.algebraicwolf.backend.storage.UserStorage
import com.algebraicwolf.backend.storage.impl.SimpleUserStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val userStorageModule = module {
    singleOf(::SimpleUserStorage) bind UserStorage::class
}