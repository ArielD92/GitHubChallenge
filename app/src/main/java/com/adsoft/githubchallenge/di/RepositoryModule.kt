package com.adsoft.githubchallenge.di

import com.adsoft.githubchallenge.room.UserRepository
import org.koin.dsl.module

fun repositoryModule() = module {
    single {
        UserRepository(get())
    }
}