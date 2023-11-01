package com.algebraicwolf.backend

import com.algebraicwolf.backend.storage.PublicationStorage
import com.algebraicwolf.backend.storage.impl.SimpleStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val publicationsModule = module {
    singleOf(::SimpleStorage) bind PublicationStorage::class
}