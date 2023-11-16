package com.algebraicwolf.backend

import com.algebraicwolf.backend.storage.PublicationStorage
import com.algebraicwolf.backend.storage.impl.DatabaseStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val publicationsModule = module {
    singleOf(::DatabaseStorage) bind PublicationStorage::class
}